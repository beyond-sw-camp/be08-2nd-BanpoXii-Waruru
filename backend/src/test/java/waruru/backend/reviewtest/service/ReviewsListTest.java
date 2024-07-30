package waruru.backend.reviewtest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import waruru.backend.review.domain.Review;
import waruru.backend.review.dto.ReviewResponseDTO;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class  ReviewsListTest extends ReviewCommonSetUp{

    @Test
    public void testGetListReviews() {

        List<Review> reviews = Arrays.asList(review, review2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Review> page = new PageImpl<>(reviews, pageable, reviews.size());
        // given
        when(reviewRepository.findAll(pageable)).thenReturn(page);

        // when
        List<ReviewResponseDTO> reviewResponseDTOList = reviewService.getAllReviews(0,10);
        // then
        assertEquals(2, reviewResponseDTOList.size());

        ReviewResponseDTO reviewResponseDTO1 = reviewResponseDTOList.get(0);
        assertEquals("1번 후기 Title Test", reviewResponseDTO1.getTitle());
        assertEquals("1번 후기 Content Test", reviewResponseDTO1.getContent());
        assertEquals(Long.valueOf(1L), reviewResponseDTO1.getUserNo());
        assertEquals(Long.valueOf(1L), reviewResponseDTO1.getSaleNo());

        ReviewResponseDTO reviewResponseDTO2 = reviewResponseDTOList.get(1);
        assertEquals("2번 후기 Title Test", reviewResponseDTO2.getTitle());
        assertEquals("2번 후기 Content Test", reviewResponseDTO2.getContent());
        assertEquals(Long.valueOf(2L), reviewResponseDTO2.getUserNo());
        assertEquals(Long.valueOf(2L), reviewResponseDTO2.getSaleNo());

        reviewResponseDTOList.forEach(System.out::println);
    }
}