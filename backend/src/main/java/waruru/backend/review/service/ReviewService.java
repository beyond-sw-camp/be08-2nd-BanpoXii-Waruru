package waruru.backend.review.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waruru.backend.review.domain.Review;
import waruru.backend.review.domain.ReviewRepository;
import waruru.backend.review.dto.ReviewRequestDTO;
import waruru.backend.review.dto.ReviewResponseDTO;
import waruru.backend.sales.domain.Sales;
import waruru.backend.sales.domain.SalesRepository;
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
    private final SalesRepository salesRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, SalesRepository salesRepository){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.salesRepository = salesRepository;
    }

    public ReviewResponseDTO createReview(ReviewRequestDTO rrq){
        Review review = new Review();

        //  예외처리 해주기
        User user = userRepository.findById(rrq.getUserNo())
                .orElseThrow(() -> new EntityNotFoundException("회원 ID 조회 불가 : " + rrq.getUserNo()));
        Sales sale = salesRepository.findById(rrq.getSaleNo())
                .orElseThrow(() -> new EntityNotFoundException("매물 번호 조회 불가 : " + rrq.getSaleNo()));


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


}
