package waruru.backend.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.business.dto.BusinessCancelRequestDTO;
import waruru.backend.common.exception.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusinessCancelTest extends BusinessCommonSetUp {

    @Test
    public void testCancelBusiness() {

        // given
        BusinessCancelRequestDTO businessCancelRequestDTO = new BusinessCancelRequestDTO();

        // when
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));

        // then
        Optional<Void> businessResponseDTO = businessService.cancelBusiness(1L, businessCancelRequestDTO);
        assertEquals(BusinessStatus.CANCEL, businessCancelRequestDTO.getStatus());
    }

    @Test
    public void testCancelBusinessNotFound() {

        // given
        Long businessNo = 10L;
        BusinessCancelRequestDTO businessCancelRequestDTO = new BusinessCancelRequestDTO();

        // when
        when(businessRepository.findById(10L)).thenReturn(Optional.empty());

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            businessService.cancelBusiness(10L, businessCancelRequestDTO);
        });

        System.out.println(thrown.getMessage());
    }
}