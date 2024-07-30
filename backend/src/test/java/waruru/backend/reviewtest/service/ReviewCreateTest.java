package waruru.backend.reviewtest.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.review.domain.Review;
import waruru.backend.review.dto.ReviewRequestDTO;
import waruru.backend.review.dto.ReviewResponseDTO;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewCreateTest extends ReviewCommonSetUp{

    @Test
    public void testCreateReview() {

        // given
        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO();
        reviewRequestDTO.setUserNo(1L);
        reviewRequestDTO.setSaleNo(1L);
        reviewRequestDTO.setTitle("1번 후기 Title Test");
        reviewRequestDTO.setContent("1번 후기 Content Test");
        reviewRequestDTO.setRegisterDate(LocalDateTime.now());

        // when
        when(memberRepository.findById(1L)).thenReturn(Optional.of(review.getUserNo()));
        when(saleRepository.findById(1L)).thenReturn(Optional.of(review.getSaleNo()));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // then
        ReviewResponseDTO reviewResponseDTO = reviewService.createReview(reviewRequestDTO);
        assertEquals("1번 후기 Title Test", reviewResponseDTO.getTitle());
        assertEquals("1번 후기 Content Test", reviewResponseDTO.getContent());
        assertEquals(Long.valueOf(1L), reviewResponseDTO.getUserNo());
        assertEquals(Long.valueOf(1L), reviewResponseDTO.getSaleNo());
        assertEquals(review.getRegisterDate(), reviewResponseDTO.getRegisterDate());
    }

    @Test
    public void testCreateReviewUserNotFound() {

        // given
        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO();

        reviewRequestDTO.setUserNo(10L); 
        reviewRequestDTO.setSaleNo(1L);
        reviewRequestDTO.setTitle("회원 없음 Title Test");
        reviewRequestDTO.setContent("회원 없음 Content Test");

        // when
        when(memberRepository.findById(10L)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            reviewService.createReview(reviewRequestDTO);
        });

        System.out.println(thrown.getMessage());
    }

    @Test
    public void testCreateReviewSaleNotFound() {

        // given
        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO();
        reviewRequestDTO.setUserNo(1L);
        reviewRequestDTO.setSaleNo(10L); 
        reviewRequestDTO.setTitle("매물 없음 Title Test");
        reviewRequestDTO.setContent("매물 없음 Content Test");

        // when
        when(memberRepository.findById(1L)).thenReturn(Optional.of(review.getUserNo()));
        when(saleRepository.findById(10L)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            reviewService.createReview(reviewRequestDTO);
        });

        System.out.println(thrown.getMessage());
    }
}
