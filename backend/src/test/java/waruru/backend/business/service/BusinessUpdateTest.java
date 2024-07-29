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

    @Test   // Business 수정 테스트
    public void testBusinessUpdate() {
        // Given
        Business existBusiness = new Business();
        existBusiness.setBusinessNo(1L);
        existBusiness.setTotalPrice(5000);
        existBusiness.setStatus(BusinessStatus.ING);
        existBusiness.setUpdatedDate(LocalDateTime.now().minusDays(1));

        BusinessUpdateRequestDTO businessUpdateRequestDTO = new BusinessUpdateRequestDTO();
        businessUpdateRequestDTO.setBusinessNo(1L);
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
        businessService.updateBusiness(businessUpdateRequestDTO);

        // Then
        assertEquals(businessUpdateRequestDTO.getTotalPrice(), existBusiness.getTotalPrice());
        assertEquals(businessUpdateRequestDTO.getStatus(), existBusiness.getStatus());
        assertEquals(businessUpdateRequestDTO.getUpdatedDate(), existBusiness.getUpdatedDate());
    }

    @Test   // 거래 내역이 존재하지 않을 때
    public void testUpdateBusinessNotFound() {
        // given
        BusinessUpdateRequestDTO businessUpdateRequestDTO = new BusinessUpdateRequestDTO();
        businessUpdateRequestDTO.setBusinessNo(1L);
        businessUpdateRequestDTO.setTotalPrice(9000);
        businessUpdateRequestDTO.setStatus(BusinessStatus.DONE);
        businessUpdateRequestDTO.setUpdatedDate(LocalDateTime.now());

        // when
        when(businessRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            businessService.updateBusiness(businessUpdateRequestDTO);
        });

        System.out.println(thrown.getMessage());
    }

}
