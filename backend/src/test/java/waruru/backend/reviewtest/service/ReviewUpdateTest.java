package waruru.backend.reviewtest.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.review.domain.Review;
import waruru.backend.review.dto.ReviewResponseDTO;
import waruru.backend.review.dto.ReviewUpdateRequestDTO;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewUpdateTest extends ReviewCommonSetUp{

    @Test
    public void testUpdateReview() {

        // given
        ReviewUpdateRequestDTO reviewUpdateRequestDTO = new ReviewUpdateRequestDTO();

        reviewUpdateRequestDTO.setTitle("수정된 매물 후기 제목");
        reviewUpdateRequestDTO.setContent("수정된 매물 후기 내용");
        reviewUpdateRequestDTO.setUpdateDate(LocalDateTime.now());

        // when
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // then
        ReviewResponseDTO reviewResponseDTO = reviewService.updateReview(1L, reviewUpdateRequestDTO);

        assertEquals("수정된 매물 후기 제목", reviewResponseDTO.getTitle());
        assertEquals("수정된 매물 후기 내용", reviewResponseDTO.getContent());
        assertEquals(review.getUpdateDate(), reviewResponseDTO.getUpdateDate());
    }

    @Test
    public void testUpdateReviewNotFound() {

        // given
        ReviewUpdateRequestDTO reviewUpdateRequestDTO = new ReviewUpdateRequestDTO();

        reviewUpdateRequestDTO.setTitle("수정된 매물 후기 제목");
        reviewUpdateRequestDTO.setContent("수정된 매물 후기 내용");
        reviewUpdateRequestDTO.setUpdateDate(LocalDateTime.now());

        // when
        when(reviewRepository.findById(10L)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            reviewService.updateReview(10L, reviewUpdateRequestDTO);
        });

        System.out.println(thrown.getMessage());
    }
}
