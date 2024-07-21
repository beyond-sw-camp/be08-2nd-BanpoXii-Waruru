package waruru.backend.sale.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.sale.domain.SaleStatus;
import waruru.backend.sale.dto.SaleListResponseDTO;
import waruru.backend.sale.dto.SaleRegisterRequestDTO;
import waruru.backend.sale.dto.SaleResponseDTO;
import waruru.backend.sale.dto.SaleUpdateRequestDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private SaleService saleService;

    private Sale existedSale;
    private Member existedMember;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        existedMember = createTestMember();
        existedSale = createTestSale(existedMember);

        // Mocking
        when(saleRepository.findById(1L)).thenReturn(Optional.of(existedSale));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(existedMember));
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void registerSale_Success() {
        // Given
        SaleRegisterRequestDTO requestDTO = new SaleRegisterRequestDTO(
                1L, "myhouse", "daegu", 33, Category.SALE, 3700, 0, 0, "good place", SaleStatus.N
        );
        Member member = new Member();
        member.setId(1L);
        member.setNickname("henhen");

        Sale sale = Sale.builder()
                .userNo(member)
                .saleName(requestDTO.getSaleName())
                .saleLocation(requestDTO.getSaleLocation())
                .area(requestDTO.getArea())
                .category(requestDTO.getCategory())
                .salePrice(requestDTO.getSalePrice())
                .depositPrice(requestDTO.getDepositPrice())
                .rentPrice(requestDTO.getRentPrice())
                .description(requestDTO.getDescription())
                .saleStatus(requestDTO.getSaleStatus())
                .build();

        when(memberRepository.findById(requestDTO.getUserNo())).thenReturn(Optional.of(member));
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);

        // When
        SaleResponseDTO responseDTO = saleService.registerSale(requestDTO);

        // Then
        assertEquals(requestDTO.getSaleName(), responseDTO.getSaleName());
        verify(memberRepository, times(1)).findById(requestDTO.getUserNo());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    public void registerSale_UserNotFound() {
        // Given
        SaleRegisterRequestDTO requestDTO = new SaleRegisterRequestDTO(
                2L, "myhouse", "daegu", 33, Category.SALE, 3700, 0, 0, "good place", SaleStatus.N
        );

        when(memberRepository.findById(requestDTO.getUserNo())).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            saleService.registerSale(requestDTO);
        });

        // Then
        assertEquals("존재하지 않는 회원입니다. no = 2", exception.getMessage());
        verify(memberRepository, times(1)).findById(requestDTO.getUserNo());
        verify(saleRepository, times(0)).save(any(Sale.class));
    }

    @Test
    void findById_Success() {
        // Given

        // When
        SaleResponseDTO findByIdSale = saleService.findById(1L);

        // Then
        assertEquals(existedSale.getNo(), findByIdSale.getNo());
        assertEquals(existedSale.getSaleName(), findByIdSale.getSaleName());
    }

    @Test
    void findById_SaleNotFound() {
        // Given
        when(saleRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                saleService.findById(2L));

        // Then
        assertEquals("존재하지 않는 매물입니다. no = 2", exception.getMessage());
    }

    @Test
    void deleteSale_Success() {
        // Given

        // When
        saleService.deleteSale(1L);

        // Then
        verify(saleRepository).findById(1L);
        verify(saleRepository).delete(existedSale);
    }

    @Test
    void deleteSale_SaleNotFound() {
        // Given

        // When
        when(saleRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> saleService.deleteSale(2L));

        // Then
        verify(saleRepository).findById(2L);
        verify(saleRepository, never()).delete(any(Sale.class));
    }

    @Test
    void findAllList_Success() {
        // Given
        Member testMember = new Member();
        testMember.setId(2L);
        testMember.setNickname("TestNick");

        Sale testSale = new Sale();
        testSale.setNo(1L);
        testSale.setSaleName("Test Sale");
        testSale.setSaleLocation("Korea");
        testSale.setArea(20);
        testSale.setCategory(Category.MONTHLY);
        testSale.setSalePrice(0);
        testSale.setDepositPrice(2000);
        testSale.setRentPrice(40);
        testSale.setDescription("Test Sale Description");
        testSale.setSaleStatus(SaleStatus.Y);
        testSale.setUserNo(testMember);

        when(saleRepository.findAll()).thenReturn(Arrays.asList(existedSale, testSale));

        // When
        List<SaleListResponseDTO> salesList = saleService.findAllList();

        // Then
        verify(saleRepository, times(1)).findAll();
        assertEquals(2, salesList.size());
        assertEquals("Test Sale", salesList.get(1).getSaleName());
    }

    @Test
    void updateSale_Success() {
        // Given
        SaleUpdateRequestDTO updateRequestDTO = new SaleUpdateRequestDTO(
                "Update Sale", "Korea", null, Category.MONTHLY, null, 5000, 30, "Update Description", SaleStatus.Y);

        // When
        saleService.updateSale(1L, updateRequestDTO);

        // Then
        verify(saleRepository).findById(1L);

        assertEquals("Update Sale", existedSale.getSaleName());
        assertEquals("Korea", existedSale.getSaleLocation());
        assertEquals(50, existedSale.getArea());
        assertEquals(Category.MONTHLY, existedSale.getCategory());
        assertEquals(0, existedSale.getSalePrice());
        assertEquals(5000, existedSale.getDepositPrice());
        assertEquals(30, existedSale.getRentPrice());
        assertEquals("Update Description", existedSale.getDescription());
        assertEquals(SaleStatus.Y, existedSale.getSaleStatus());
    }

    private Member createTestMember() {
        Member member = new Member();
        member.setId(1L);
        member.setNickname("OldNick");
        return member;
    }

    private Sale createTestSale(Member member) {
        Sale sale = new Sale();
        sale.setNo(1L);
        sale.setSaleName("Existed Sale");
        sale.setSaleLocation("Korea");
        sale.setArea(50);
        sale.setCategory(Category.SALE);
        sale.setSalePrice(0);
        sale.setDepositPrice(3000);
        sale.setRentPrice(50);
        sale.setDescription("Existed Sale Description");
        sale.setSaleStatus(SaleStatus.Y);
        sale.setUserNo(member);
        return sale;
    }

}