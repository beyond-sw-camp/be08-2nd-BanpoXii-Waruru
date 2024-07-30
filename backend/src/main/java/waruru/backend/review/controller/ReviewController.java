package waruru.backend.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.review.dto.ReviewDeleteRequestDTO;
import waruru.backend.review.dto.ReviewRequestDTO;
import waruru.backend.review.dto.ReviewResponseDTO;
import waruru.backend.review.dto.ReviewUpdateRequestDTO;
import waruru.backend.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/v1/api/reviews")
@Tag(name = "Review", description = "매물 후기 관리")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/register")
    @Operation(summary = "매물 후기를 등록하는 API")
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {

        ReviewResponseDTO response = reviewService.createReview(reviewRequestDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    @Operation(summary = "모든 후기의 리스트를 반환하는 API")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews(@RequestParam(value="page", defaultValue="0") int page,
                                                                 @RequestParam(value="size", defaultValue="0") int size) {
        List<ReviewResponseDTO> reviews = reviewService.getAllReviews(page,size);

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewNo}")
    @Operation(summary = "특정 후기의 정보를 반환하는 API")

    public ReviewResponseDTO getReview(@PathVariable Long reviewNo) {

        return reviewService.getReviewById(reviewNo);
    }

    @PutMapping("/update/{reviewNo}")
    @Operation(summary = "매물 후기를 수정하는 API")
    public ReviewResponseDTO updateReview(@PathVariable Long reviewNo, @RequestBody ReviewUpdateRequestDTO reviewUpdateRequestDTO) {

        return reviewService.updateReview(reviewNo, reviewUpdateRequestDTO);
    }

    @DeleteMapping("/delete/{reviewNo}")
    @Operation(summary = "매물 후기를 삭제하는 API")
    public void deleteReview(@PathVariable Long reviewNo,@RequestBody ReviewDeleteRequestDTO reviewDeleteRequestDTO) {

        reviewService.deleteReview(reviewNo, reviewDeleteRequestDTO);
    }
}
