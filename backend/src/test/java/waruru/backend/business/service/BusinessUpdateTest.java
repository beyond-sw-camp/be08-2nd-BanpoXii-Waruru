package waruru.backend.business.service;

import org.junit.jupiter.api.Test;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.business.dto.BusinessUpdateRequestDTO;
import waruru.backend.common.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BusinessUpdateTest extends BusinessCommonSetUp {

    @Test
    public void testBusinessUpdate() {

        // given
        Business existBusiness = new Business();

        existBusiness.setBusinessNo(1L);
        existBusiness.setTotalPrice(5000);
        existBusiness.setStatus(BusinessStatus.ING);
        existBusiness.setUpdatedDate(LocalDateTime.now().minusDays(1));

        BusinessUpdateRequestDTO businessUpdateRequestDTO = new BusinessUpdateRequestDTO();
        businessUpdateRequestDTO.setTotalPrice(9000);
        businessUpdateRequestDTO.setStatus(BusinessStatus.DONE);
        businessUpdateRequestDTO.setUpdatedDate(LocalDateTime.now());

        when(businessRepository.findById(1L)).thenReturn(Optional.of(existBusiness));
        when(businessRepository.save(any(Business.class))).thenAnswer(invocation -> {
            Business updatedBusiness = invocation.getArgument(0);
            existBusiness.setTotalPrice(updatedBusiness.getTotalPrice());
            existBusiness.setStatus(updatedBusiness.getStatus());
            existBusiness.setUpdatedDate(updatedBusiness.getUpdatedDate());
            return existBusiness;
        });

        // when
        businessService.updateBusiness(1L, businessUpdateRequestDTO);

        // Then
        assertEquals(businessUpdateRequestDTO.getTotalPrice(), existBusiness.getTotalPrice());
        assertEquals(businessUpdateRequestDTO.getStatus(), existBusiness.getStatus());
        assertEquals(businessUpdateRequestDTO.getUpdatedDate(), existBusiness.getUpdatedDate());
    }

    @Test
    public void testUpdateBusinessNotFound() {

        // given
        BusinessUpdateRequestDTO businessUpdateRequestDTO = new BusinessUpdateRequestDTO();
        businessUpdateRequestDTO.setTotalPrice(9000);
        businessUpdateRequestDTO.setStatus(BusinessStatus.DONE);
        businessUpdateRequestDTO.setUpdatedDate(LocalDateTime.now());

        // when
        when(businessRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            businessService.updateBusiness(1L, businessUpdateRequestDTO);
        });

        System.out.println(thrown.getMessage());
    }
}
