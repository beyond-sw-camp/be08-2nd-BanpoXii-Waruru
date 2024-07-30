package waruru.backend.review.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.review.domain.Review;
import waruru.backend.review.domain.ReviewRepository;
import waruru.backend.review.dto.ReviewDeleteRequestDTO;
import waruru.backend.review.dto.ReviewRequestDTO;
import waruru.backend.review.dto.ReviewResponseDTO;
import waruru.backend.review.dto.ReviewUpdateRequestDTO;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final MemberRepository memberRepository;

    private final SaleRepository saleRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, MemberRepository memberRepository, SaleRepository salesRepository){

        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.saleRepository = salesRepository;
    }

    @Transactional
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO){

        boolean reviewExists = reviewRepository.existsByUserNo_IdAndSaleNo_SaleNo(reviewRequestDTO.getUserNo(), reviewRequestDTO.getSaleNo());

        if (reviewExists) {

            throw new IllegalStateException("해당 매물에 관해 작성한 후기가 존재합니다.");
        }

        Member member = memberRepository.findById(reviewRequestDTO.getUserNo())
                .orElseThrow(() -> new EntityNotFoundException("회원 ID 조회 불가: " + reviewRequestDTO.getUserNo()));

        Sale sale = saleRepository.findById(reviewRequestDTO.getSaleNo())
                .orElseThrow(() -> new EntityNotFoundException("매물 번호 조회 불가: " + reviewRequestDTO.getSaleNo()));

        Review review = new Review();
        review.setUserNo(member);
        review.setSaleNo(sale);
        review.setTitle(reviewRequestDTO.getTitle());
        review.setContent(reviewRequestDTO.getContent());
        review.setRegisterDate(reviewRequestDTO.getRegisterDate());

        review = reviewRepository.save(review);

        return new ReviewResponseDTO(review);
    }

    public List<ReviewResponseDTO> getAllReviews(int page,int size) {

        Pageable pageable = PageRequest.of(page, size);

        return reviewRepository.findAll(pageable).stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
    }


    public ReviewResponseDTO getReviewById(Long reviewNo){

        Review review =  reviewRepository.findById(reviewNo)
                .orElseThrow(() -> new EntityNotFoundException("매물 후기 번호 조회 불가: " + reviewNo));

        return new ReviewResponseDTO(review);
    }

    @Transactional
    public ReviewResponseDTO updateReview(Long reviewNo, ReviewUpdateRequestDTO reviewUpdateRequestDTO) {

        Review review = reviewRepository.findById(reviewNo)
                .orElseThrow(() -> new EntityNotFoundException("매물 후기 수정 불가: " + reviewNo));

        if (!review.getUserNo().getId().equals(reviewUpdateRequestDTO.getUserNo())) {

            throw new SecurityException("후기를 수정할 권한이 없습니다.");
        }

        review.setTitle(reviewUpdateRequestDTO.getTitle());
        review.setContent(reviewUpdateRequestDTO.getContent());
        review.setUpdateDate(reviewUpdateRequestDTO.getUpdateDate());

        Review updatedReview = reviewRepository.save(review);

        return new ReviewResponseDTO(updatedReview);
    }

    @Transactional
    public void deleteReview(Long reviewNo, ReviewDeleteRequestDTO deleteReviewRequestDTO) {

        Review review = reviewRepository.findById(reviewNo)
                .orElseThrow(() -> new EntityNotFoundException("매물 후기 삭제 불가: " + reviewNo));

        if (!review.getUserNo().getId().equals(deleteReviewRequestDTO.getUserNo())) {

            throw new SecurityException("후기를 삭제할 권한이 없습니다.");
        }

        reviewRepository.delete(review);
    }
}
