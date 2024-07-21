package waruru.backend.reviewtest.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import waruru.backend.review.dto.ReviewDeleteRequestDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class ReviewDeleteTest extends ReviewCommonSetUp{

    @Test
    public void testDeleteReview() {
        //given, 삭제할 리뷰 정의, 지정된 리뷰 객체 반환
        Long reviewNo = 2L;
        ReviewDeleteRequestDTO deleteRequestDTO = new ReviewDeleteRequestDTO();
        deleteRequestDTO.setReviewNo(reviewNo);
        when(reviewRepository.findById(reviewNo)).thenReturn(Optional.of(review2));

        // when
        reviewService.deleteReview(deleteRequestDTO);

        // then, 리뷰 삭제 검증(메소드 호출 횟수)
        verify(reviewRepository, times(1)).delete(review2);
    }

    @Test
    public void testDeleteReviewNotFound() {
        // given, 존재하지 않는 매물 후기 정의
        Long reviewNo = 10L;

        ReviewDeleteRequestDTO deleteRequestDTO = new ReviewDeleteRequestDTO();
        deleteRequestDTO.setReviewNo(reviewNo);

        // when, 존재하지 않는 회원 ID
        when(reviewRepository.findById(reviewNo)).thenReturn(Optional.empty());

        // then, 테스트 실패 -> 예외 발생으로 검증
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            reviewService.deleteReview(deleteRequestDTO);
        });

        System.out.println(thrown.getMessage());
//        assertEquals("예외처리 내용 불일치 => fail" + reviewNo, thrown.getMessage());
    }
}
