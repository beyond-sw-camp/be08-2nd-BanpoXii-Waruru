package waruru.backend.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.review.domain.Review;
import waruru.backend.review.dto.ReviewRequestDTO;
import waruru.backend.review.dto.ReviewResponseDTO;
import waruru.backend.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review 관리", description = "리뷰 관리를 위한 api")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    @Operation(summary = "새 리뷰 등록", description = "[설명] /api/reviews 새 리뷰 등록")
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        ReviewResponseDTO response = reviewService.createReview(reviewRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    @Operation(summary = "모든 리뷰 조회하기", description = "[설명] /api/reviews/list 모든 리뷰 조회하기")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        List<ReviewResponseDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }


}
