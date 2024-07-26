package waruru.backend.detail.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.detail.dto.DetailResponseDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetailReadTest extends DetailCommonSetUp {

    @Test
    public void getAllDetails() {

        // given
        when(detailRepository.findAll()).thenReturn(Arrays.asList(detail, detail2));

        // when
        List<DetailResponseDTO> details = detailService.getAllDetails();

        // then
        assertEquals(2, details.size());

        // 첫 번째 납부 내역
        DetailResponseDTO detailDTO = details.get(0);
        assertEquals(Long.valueOf(1L), detailDTO.getId());
        assertEquals("test", detailDTO.getTitle());
        assertEquals("test", detailDTO.getCategory());
        assertEquals("test", detailDTO.getDescription());
        assertEquals(100, detailDTO.getPrice());
        assertEquals("2024-07-21", detailDTO.getDetailDate());

        // 두 번째 납부 내역
        DetailResponseDTO detailDTO2 = details.get(1);
        assertEquals(Long.valueOf(2L), detailDTO2.getId());
        assertEquals("test2", detailDTO2.getTitle());
        assertEquals("test2", detailDTO2.getCategory());
        assertEquals("test2", detailDTO2.getDescription());
        assertEquals(200, detailDTO2.getPrice());
        assertEquals("2024-07-22", detailDTO2.getDetailDate());

        details.forEach(System.out::println);
    }

    @Test
    public void getDetailById() {

        // given
        Long detailId = 1L;

        // when
        when(detailRepository.findById(detailId)).thenReturn(Optional.of(detail));

        //then
        DetailResponseDTO detailResponseDTO = detailService.getDetailById(detailId);

        assertEquals(detail.getId(), detailResponseDTO.getId());
        assertEquals(detail.getTitle(), detailResponseDTO.getTitle());
        assertEquals(detail.getCategory(), detailResponseDTO.getCategory());
        assertEquals(detail.getDescription(), detailResponseDTO.getDescription());
        assertEquals(detail.getPrice(), detailResponseDTO.getPrice());
        assertEquals(detail.getDetailDate(), detailResponseDTO.getDetailDate());

        System.out.println(detailResponseDTO);
    }

    @Test
    public void NotFoundById() {

        // given
        Long detailId = 10L;

        // when
        when(detailRepository.findById(detailId)).thenReturn(Optional.empty());

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class,
                () -> detailService.getDetailById(detailId));

        System.out.println(thrown.getMessage());
    }
}