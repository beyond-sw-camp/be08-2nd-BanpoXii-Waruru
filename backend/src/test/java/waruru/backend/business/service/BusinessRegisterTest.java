package waruru.backend.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.business.dto.BusinessRegisterRequestDTO;
import waruru.backend.business.dto.BusinessResponseDTO;
import waruru.backend.common.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusinessRegisterTest extends BusinessCommonSetUp {
    
    @Test
    public void testRegister() {

        // given
        BusinessRegisterRequestDTO businessRegisterRequestDTO = new BusinessRegisterRequestDTO();
        businessRegisterRequestDTO.setUserNo(1L);
        businessRegisterRequestDTO.setSaleNo(1L);
        businessRegisterRequestDTO.setTotalPrice(5000);
        businessRegisterRequestDTO.setStatus(BusinessStatus.ING);
        businessRegisterRequestDTO.setCreatedDate(LocalDateTime.now());

        // when
        when(memberRepository.findById(1L)).thenReturn(Optional.of(business.getUserNo()));
        when(saleRepository.findById(1L)).thenReturn(Optional.of(business.getSaleNo()));
        when(businessRepository.save(any(Business.class))).thenReturn(business);

        // then
        BusinessResponseDTO businessResponseDTO = businessService.registerBusiness(businessRegisterRequestDTO);
        assertEquals(Long.valueOf(5000), businessResponseDTO.getTotalPrice());
        assertEquals(BusinessStatus.ING, businessResponseDTO.getStatus());
        assertEquals(Long.valueOf(1L), businessResponseDTO.getUserNo());
        assertEquals(Long.valueOf(1L), businessResponseDTO.getSaleNo());
        assertEquals(business.getCreatedDate(), businessResponseDTO.getCreatedDate());
    }

    @Test
    public void testRegisterBusinessUserNotFound() {

        // given
        BusinessRegisterRequestDTO businessRegisterRequestDTO = new BusinessRegisterRequestDTO();
        businessRegisterRequestDTO.setUserNo(10L);
        businessRegisterRequestDTO.setSaleNo(1L);
        businessRegisterRequestDTO.setTotalPrice(5000);
        businessRegisterRequestDTO.setStatus(BusinessStatus.ING);
        businessRegisterRequestDTO.setCreatedDate(LocalDateTime.now());

        // when
        when(memberRepository.findById(10L)).thenReturn(Optional.empty());

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            businessService.registerBusiness(businessRegisterRequestDTO);
        });

        System.out.println(thrown.getMessage());
    }

    @Test
    public void testRegisterBusinessSaleNotFound() {

        // given
        BusinessRegisterRequestDTO businessRegisterRequestDTO = new BusinessRegisterRequestDTO();
        businessRegisterRequestDTO.setUserNo(1L);
        businessRegisterRequestDTO.setSaleNo(10L);
        businessRegisterRequestDTO.setTotalPrice(5000);
        businessRegisterRequestDTO.setStatus(BusinessStatus.ING);
        businessRegisterRequestDTO.setCreatedDate(LocalDateTime.now());

        // when
        when(memberRepository.findById(1L)).thenReturn(Optional.of(business.getUserNo()));
        when(saleRepository.findById(10L)).thenReturn(Optional.empty());

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            businessService.registerBusiness(businessRegisterRequestDTO);
        });

        System.out.println(thrown.getMessage());
    }

}
