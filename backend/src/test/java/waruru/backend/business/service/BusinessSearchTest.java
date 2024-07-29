package waruru.backend.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.business.dto.BusinessListResponseDTO;
import waruru.backend.business.dto.BusinessResponseDTO;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.member.domain.Member;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessSearchTest extends BusinessCommonSetUp {

    @Test
    public void testSearchBusiness() {

        // given
        Long businessNo = 1L;

        // when
        when(businessRepository.findById(business.getBusinessNo())).thenReturn(Optional.of(business));
        when(memberRepository.findById(business.getUserNo().getId())).thenReturn(Optional.of(business.getUserNo()));
        when(saleRepository.findById(business.getSaleNo().getSaleNo())).thenReturn(Optional.of(business.getSaleNo()));

        BusinessResponseDTO businessResponseDTO = businessService.findBusinessByBusinessNo(businessNo);

        // then
        assertEquals(business.getBusinessNo(), businessResponseDTO.getBusinessNo());

        assertEquals(business.getTotalPrice(), businessResponseDTO.getTotalPrice());
        assertEquals(business.getStatus(), businessResponseDTO.getStatus());
        assertEquals(business.getCreatedDate(), businessResponseDTO.getCreatedDate());
        assertEquals(business.getUpdatedDate(), businessResponseDTO.getUpdatedDate());

        assertEquals(business.getUserNo().getId(), businessResponseDTO.getUserNo());
        assertEquals(business.getUserNo().getName(), businessResponseDTO.getName());

        assertEquals(business.getSaleNo().getSaleNo(), businessResponseDTO.getSaleNo());
        assertEquals(business.getSaleNo().getSaleName(), businessResponseDTO.getSaleName());
        assertEquals(business.getSaleNo().getSalePrice(), businessResponseDTO.getSalePrice());
        assertEquals(business.getSaleNo().getRentPrice(), businessResponseDTO.getRentPrice());
        assertEquals(business.getSaleNo().getDepositPrice(), businessResponseDTO.getDepositPrice());
        assertEquals(business.getSaleNo().getSaleLocation(), businessResponseDTO.getSaleLocation());
        assertEquals(business.getSaleNo().getSaleStatus(), businessResponseDTO.getSaleStatus());
        assertEquals(business.getSaleNo().getArea(), businessResponseDTO.getArea());
        assertEquals(business.getSaleNo().getCategory(), businessResponseDTO.getCategory());
        assertEquals(business.getSaleNo().getDescription(), businessResponseDTO.getDescription());
    }

    @Test
    public void testSearchBusinessNotFound() {

        // given
        Long businessNo = 10L;

        // when
        when(businessRepository.findById(10L)).thenReturn(Optional.empty());

        // then
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            businessService.findBusinessByBusinessNo(businessNo);
        });

        System.out.println(thrown.getMessage());
    }

    @Test
    public void testSearchListBusiness() {

        // given
        Member member = new Member();
        member.setId(1L);

        Sale sale = new Sale();
        sale.setSaleNo(1L);
        sale.setSaleName("Test Sale");
        sale.setSaleLocation("Test Location");
        sale.setArea(10);
        sale.setCategory(Category.MONTHLY);
        sale.setSalePrice(5000);
        sale.setDepositPrice(1000);
        sale.setRentPrice(500);
        sale.setDescription("Test Description");
        sale.setSaleStatus(SaleStatus.Y);

        Business testBusiness = new Business();
        testBusiness.setBusinessNo(4L);
        testBusiness.setStatus(BusinessStatus.ING);
        testBusiness.setTotalPrice(5000);
        testBusiness.setUpdatedDate(LocalDateTime.now());
        testBusiness.setUserNo(member);
        testBusiness.setSaleNo(sale);

        when(businessRepository.findByUserNo_Id(1L)).thenReturn(Arrays.asList(testBusiness));

        // when
        List<BusinessListResponseDTO> businessListResponseDTOs = businessService.findAllList(1L);

        // Then
        verify(businessRepository, times(1)).findByUserNo_Id(1L);
        assertEquals(1, businessListResponseDTOs.size());
    }
}
