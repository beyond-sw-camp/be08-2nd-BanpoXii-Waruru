package waruru.backend.detail.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.common.exception.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetailDeleteTest extends DetailCommonSetUp {

    @Test
    public void deleteDetail_success() {

        // given
        Long detailId = detail.getDetailNo();
        when(detailRepository.findById(detailId)).thenReturn(Optional.of(detail));

        // when
        detailService.deleteDetail(detailId);

        // then
        verify(detailRepository, times(1)).delete(detail);
    }

    @Test
    public void deleteDetail_notFound() {

        // given
        Long detailId = 999L;
        when(detailRepository.findById(detailId)).thenReturn(Optional.empty());

        // when & then
        NotFoundException thrown = assertThrows(NotFoundException.class,
                () -> detailService.deleteDetail(detailId));

        System.out.println(thrown.getMessage());
    }
}
