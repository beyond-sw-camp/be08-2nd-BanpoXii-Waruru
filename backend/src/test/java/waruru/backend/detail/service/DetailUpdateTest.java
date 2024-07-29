package waruru.backend.detail.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.detail.domain.Detail;
import waruru.backend.detail.dto.DetailUpdateRequestDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetailUpdateTest extends DetailCommonSetUp {

    @Test
    public void updateDetail() {

        // given
        DetailUpdateRequestDTO detailUpdateRequestDTO = new DetailUpdateRequestDTO(
                "updateTest", "updateTest", "updateTest", 300, "2024-07-27"
        );

        Detail existDetail = Detail.builder()
                .title("test")
                .category("test")
                .description("test")
                .price(100)
                .detailDate("2024-07-21")
                .build();

        // when
        when(detailRepository.findById(anyLong())).thenReturn(Optional.of(existDetail));

        // then
        detailService.updateDetail(1L, detailUpdateRequestDTO);

        verify(detailRepository, times(1)).save(any(Detail.class));

        assertEquals("updateTest", existDetail.getTitle());
        assertEquals("updateTest", existDetail.getCategory());
        assertEquals("updateTest", existDetail.getDescription());
        assertEquals(300, existDetail.getPrice());
        assertEquals("2024-07-27", existDetail.getDetailDate());
    }

    @Test
    public void NotFoundDetailId() {

        // given
        DetailUpdateRequestDTO detailUpdateRequestDTO = new DetailUpdateRequestDTO(
                "updateTest", "updateTest", "updateTest", 300, "2024-07-27"
        );

        // when
        when(detailRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> detailService.updateDetail(1L, detailUpdateRequestDTO));

        System.out.println(thrown);
    }
}
