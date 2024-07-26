package waruru.backend.detail.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.detail.dto.DetailDeleteRequestDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetailDeleteTest extends DetailCommonSetUp {

    @Test
    public void deleteDetail() {

        // given
        DetailDeleteRequestDTO detailDeleteRequestDTO = new DetailDeleteRequestDTO(2L);

        // when
        when(detailRepository.findById(anyLong())).thenReturn(Optional.of(detail2));
        detailService.deleteDetail(detailDeleteRequestDTO);

        //then
        verify(detailRepository, times(1)).delete(detail2);
    }

    @Test
    public void NotFoundDetail() {

        // given
        DetailDeleteRequestDTO detailDeleteRequestDTO = new DetailDeleteRequestDTO(2L);

        // when
        when(detailRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> detailService.deleteDetail(detailDeleteRequestDTO));

        System.out.println(thrown);
    }
}
