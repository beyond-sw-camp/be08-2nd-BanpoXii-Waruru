package waruru.backend.detail.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.detail.domain.Detail;
import waruru.backend.detail.domain.DetailRepository;
import waruru.backend.detail.dto.DetailRegisterRequestDTO;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetailServiceTest {

    @Mock
    private DetailRepository detailRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private DetailService detailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("납부 내역을 등록한다.")
    @Test
    void registDetail() {
        // Given
        DetailRegisterRequestDTO detailRegisterRequestDTO = new DetailRegisterRequestDTO(
                1L, 1L, "test", "test", "test", 10, "2024-07-17"
        );

        Sale sale = new Sale();
        Member member = new Member();

        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // When
        Optional<Detail> result = detailService.registerDetail(detailRegisterRequestDTO);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("test");
        assertThat(result.get().getCategory()).isEqualTo("test");
        assertThat(result.get().getDescription()).isEqualTo("test");
        assertThat(result.get().getPrice()).isEqualTo(10);
        assertThat(result.get().getDetailDate()).isEqualTo("2024-07-17");
    }
}
