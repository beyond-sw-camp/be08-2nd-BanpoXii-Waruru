package waruru.backend.detail.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.detail.domain.Detail;
import waruru.backend.detail.dto.DetailRegisterRequestDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetailRegisterTest extends DetailCommonSetUp {

    @Test
    public void registerDetail() {

        // given
        DetailRegisterRequestDTO detailRegisterRequestDTO = new DetailRegisterRequestDTO(
                1L, 1L, "test", "test", "test", 100, "2024-07-21"
        );

        // when
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(saleRepository.findById(anyLong())).thenReturn(Optional.of(sale));
        when(detailRepository.save(any())).thenReturn(detail);

        // then
        Optional<Detail> detailResponseDTO = detailService.registerDetail(detailRegisterRequestDTO);
        assertEquals("test", detailResponseDTO.get().getTitle());
        assertEquals("test", detailResponseDTO.get().getCategory());
        assertEquals("test", detailResponseDTO.get().getDescription());
        assertEquals(100L, detailResponseDTO.get().getPrice());
        assertEquals("2024-07-21", detailResponseDTO.get().getDetailDate());
    }

    @Test
    public void UserNotFound() {

        // given
        DetailRegisterRequestDTO detailRegisterRequestDTO = new DetailRegisterRequestDTO(
                1L, 1L, "test", "test", "test", 100, "2024-07-21"
        );
        // when
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class,
                () -> detailService.registerDetail(detailRegisterRequestDTO));

        System.out.println(thrown.getMessage());
    }
}