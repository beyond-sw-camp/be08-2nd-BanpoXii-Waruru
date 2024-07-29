package waruru.backend.reviewtest.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import waruru.backend.review.dto.ReviewDeleteRequestDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ReviewDeleteTest extends ReviewCommonSetUp{

    @Test
    public void testDeleteReview() {

        //given
        Long reviewNo = 2L;

        ReviewDeleteRequestDTO deleteRequestDTO = new ReviewDeleteRequestDTO();

        deleteRequestDTO.setReviewNo(reviewNo);

        when(reviewRepository.findById(reviewNo)).thenReturn(Optional.of(review2));

        // when
        reviewService.deleteReview(reviewNo, deleteRequestDTO);

        // then, 리뷰 삭제 검증(메소드 호출 횟수)
        verify(reviewRepository, times(1)).delete(review2);
    }

    @Test
    public void testDeleteReviewNotFound() {

        // given
        Long reviewNo = 10L;

        ReviewDeleteRequestDTO deleteRequestDTO = new ReviewDeleteRequestDTO();
        deleteRequestDTO.setReviewNo(reviewNo);

        // when
        when(reviewRepository.findById(reviewNo)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            reviewService.deleteReview(reviewNo, deleteRequestDTO);
        });

        System.out.println(thrown.getMessage());
    }
}
