package waruru.backend.business.service;

import org.junit.jupiter.api.Test;
import waruru.backend.business.domain.Business;
import waruru.backend.common.exception.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BusinessDeleteTest extends BusinessCommonSetUp {

    @Test   // Business 삭제 테스트
    public void testDeleteBusiness() {
        // given
        Long businessNo = 2L;
        Business business = new Business();
        business.setBusinessNo(businessNo);

        when(businessRepository.findById(businessNo)).thenReturn(Optional.of(business));

        // when
        businessService.deleteBusiness(businessNo);

        // then
        verify(businessRepository).findById(businessNo);
        verify(businessRepository).delete(business);
    }

    @Test   // 거래 내역이 존재하지 않을 때
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
