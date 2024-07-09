package waruru.backend.review.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waruru.backend.review.domain.Review;
import waruru.backend.review.domain.ReviewRepository;
import waruru.backend.review.dto.ReviewDeleteRequestDTO;
import waruru.backend.review.dto.ReviewRequestDTO;
import waruru.backend.review.dto.ReviewResponseDTO;
import waruru.backend.review.dto.ReviewUpdateRequestDTO;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.user.domain.User;
import waruru.backend.user.domain.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class ReviewService {

    // 비즈니스 로직 수행을 위한
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, SaleRepository salesRepository){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.saleRepository = salesRepository;
    }

    @Transactional
    public ReviewResponseDTO createReview(ReviewRequestDTO rrq){
        Review review = new Review();

        //  예외처리 해주기
        User user = userRepository.findById(rrq.getUserNo())
                .orElseThrow(() -> new EntityNotFoundException("회원 ID 조회 불가: " + rrq.getUserNo()));
        Sale sale = saleRepository.findById(rrq.getSaleNo())
                .orElseThrow(() -> new EntityNotFoundException("매물 번호 조회 불가: " + rrq.getSaleNo()));

        review.setUserNo(user);
        review.setSaleNo(sale);
        review.setTitle(rrq.getTitle());
        review.setContent(rrq.getContent());
        review.setRegisterDate(rrq.getRegisterDate());

        review = reviewRepository.save(review);

        return new ReviewResponseDTO(review);
    }

    public List<ReviewResponseDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
    }

    //매물 후기 수정
    public ReviewResponseDTO updateReview(Long reviewNo, ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        Review review = reviewRepository.findById(reviewNo)
                .orElseThrow(() -> new EntityNotFoundException("매물 후기 수정 불가: " + reviewNo));

        review.setTitle(reviewUpdateRequestDTO.getTitle());
        review.setContent(reviewUpdateRequestDTO.getContent());
        review.setUpdateDate(reviewUpdateRequestDTO.getUpdateDate());

        Review updatedReview = reviewRepository.save(review);

        //feat: 나중에 리뷰작성한 유저인지 확인 후 수정하게끔 로직 구현하기

        return new ReviewResponseDTO(updatedReview);
    }


    // 매물 후기 삭제
    public void deleteReview(ReviewDeleteRequestDTO deleteReviewRequestDTO) {
        Review review = reviewRepository.findById(deleteReviewRequestDTO.getReviewNo())
                .orElseThrow(() -> new EntityNotFoundException("매물 후기 삭제 불가: " + deleteReviewRequestDTO.getReviewNo()));

        reviewRepository.delete(review);
    }

}
