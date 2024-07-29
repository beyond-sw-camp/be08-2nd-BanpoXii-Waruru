package waruru.backend.reviewtest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.review.dto.ReviewResponseDTO;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewsListTest extends ReviewCommonSetUp{

    @Test
    public void testGetListReviews() {

        // given
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review, review2));

        // when
        List<ReviewResponseDTO> reviewResponseDTOList = reviewService.getAllReviews();

        // then
        assertEquals(2, reviewResponseDTOList.size());

        ReviewResponseDTO reviewResponseDTO1 = reviewResponseDTOList.get(0);
        assertEquals("1번 후기 Title Test", reviewResponseDTO1.getTitle());
        assertEquals("1번 후기 Content Test", reviewResponseDTO1.getContent());
        assertEquals(Long.valueOf(1L), reviewResponseDTO1.getUserId());
        assertEquals(Long.valueOf(1L), reviewResponseDTO1.getSaleNo());

        ReviewResponseDTO reviewResponseDTO2 = reviewResponseDTOList.get(1);
        assertEquals("2번 후기 Title Test", reviewResponseDTO2.getTitle());
        assertEquals("2번 후기 Content Test", reviewResponseDTO2.getContent());
        assertEquals(Long.valueOf(2L), reviewResponseDTO2.getUserId());
        assertEquals(Long.valueOf(2L), reviewResponseDTO2.getSaleNo());

        reviewResponseDTOList.forEach(System.out::println);
    }
}