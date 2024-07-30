package waruru.backend.detail.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.detail.domain.Detail;
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
        List<Detail> details = Arrays.asList(detail, detail2);
        Pageable pageable = PageRequest.of(1, 10);
        Page<Detail> page = new PageImpl<>(details, pageable, details.size());

        when(detailRepository.findAll(pageable)).thenReturn(page);

        // when
        List<DetailResponseDTO> detailResponseDTOs = detailService.getAllDetails(1, 10);

        // then
        assertEquals(2, detailResponseDTOs.size());

        DetailResponseDTO detailDTO = detailResponseDTOs.get(0);
        assertEquals(Long.valueOf(1L), detailDTO.getDetailNo());
        assertEquals("test", detailDTO.getTitle());
        assertEquals("test", detailDTO.getCategory());
        assertEquals("test", detailDTO.getDescription());
        assertEquals(100, detailDTO.getPrice());
        assertEquals("2024-07-21", detailDTO.getDetailDate());

        DetailResponseDTO detailDTO2 = detailResponseDTOs.get(1);
        assertEquals(Long.valueOf(2L), detailDTO2.getDetailNo());
        assertEquals("test2", detailDTO2.getTitle());
        assertEquals("test2", detailDTO2.getCategory());
        assertEquals("test2", detailDTO2.getDescription());
        assertEquals(200, detailDTO2.getPrice());
        assertEquals("2024-07-22", detailDTO2.getDetailDate());

        detailResponseDTOs.forEach(System.out::println);
    }

    @Test
    public void getDetailById() {

        // given
        Long detailId = 1L;

        // when
        when(detailRepository.findById(detailId)).thenReturn(Optional.of(detail));

        //then
        DetailResponseDTO detailResponseDTO = detailService.getDetailById(detailId);

        assertEquals(detail.getDetailNo(), detailResponseDTO.getDetailNo());
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