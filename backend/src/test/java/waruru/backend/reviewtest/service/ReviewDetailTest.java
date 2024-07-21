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
        assertEquals(review.getUserNo().getId(), reviewResponseDTO.getUserId());
        assertEquals(review.getSaleNo().getNo(), reviewResponseDTO.getSaleNo());
        assertEquals(review.getTitle(), reviewResponseDTO.getTitle());
        assertEquals(review.getContent(), reviewResponseDTO.getContent());
        assertEquals(review.getRegisterDate(), reviewResponseDTO.getRegisterDate());
        assertEquals(review.getUpdateDate(), reviewResponseDTO.getUpdateDate());

        System.out.println(reviewResponseDTO);
    }

    @Test
    public void testGetReviewDetailNotFound() {
        // given, 조회할 매물 후기 정의
        Long reviewNo = 10L;

        // when, 매물 후기 번호 존재 x
        when(reviewRepository.findById(reviewNo)).thenReturn(Optional.empty());

        // then, 테스트 실패 -> 예외 발생으로 검증
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            reviewService.getReviewById(reviewNo);
        });

        System.out.println(thrown.getMessage());
//        assertEquals("" + reviewNo, thrown.getMessage());
    }
}
