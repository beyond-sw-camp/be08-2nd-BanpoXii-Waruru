package waruru.backend.business.service;

import org.junit.jupiter.api.Test;
import waruru.backend.business.domain.Business;
import waruru.backend.common.exception.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BusinessDeleteTest extends BusinessCommonSetUp {

    @Test
    public void testDeleteBusiness() {

        // given
        Long businessNo = 2L;
        Business business = new Business();
        business.setBusinessNo(businessNo);

        when(businessRepository.findById(businessNo)).thenReturn(Optional.of(business));

        // when
        businessService.deleteBusiness(businessNo);

        // then
        verify(businessRepository, times(1)).delete(business);
        System.out.println("삭제된 거래 내역 번호: " + businessNo);
    }

    @Test
    public void testDeleteBusinessNotFound() {

        // given

        // when
        when(businessRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> businessService.deleteBusiness(10L));

        // then
        verify(businessRepository).findById(10L);
        verify(businessRepository, never()).delete(any(Business.class));
    }
}
