package waruru.backend.reviewtest.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import waruru.backend.review.dto.ReviewResponseDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ReviewDetailTest extends ReviewCommonSetUp{

    @Test
    public void testGetReviewDetail() {

        // given
        Long reviewNo = 1L;

        // when
        when(reviewRepository.findById(reviewNo)).thenReturn(Optional.of(review));

        ReviewResponseDTO reviewResponseDTO = reviewService.getReviewById(reviewNo);

        // then
        assertEquals(review.getReviewNo(), reviewResponseDTO.getReviewNo());
        assertEquals(review.getUserNo().getId(), reviewResponseDTO.getUserNo());
        assertEquals(review.getSaleNo().getSaleNo(), reviewResponseDTO.getSaleNo());
        assertEquals(review.getTitle(), reviewResponseDTO.getTitle());
        assertEquals(review.getContent(), reviewResponseDTO.getContent());
        assertEquals(review.getRegisterDate(), reviewResponseDTO.getRegisterDate());
        assertEquals(review.getUpdateDate(), reviewResponseDTO.getUpdateDate());

        System.out.println(reviewResponseDTO);
    }

    @Test
    public void testGetReviewDetailNotFound() {

        // given
        Long reviewNo = 10L;

        // when
        when(reviewRepository.findById(reviewNo)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            reviewService.getReviewById(reviewNo);
        });

        System.out.println(thrown.getMessage());
    }
}
