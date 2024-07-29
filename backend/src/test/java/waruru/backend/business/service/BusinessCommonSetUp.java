package waruru.backend.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessRepository;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.sale.domain.SaleStatus;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public abstract class BusinessCommonSetUp {

    @Mock
    protected BusinessRepository businessRepository;

    @Mock
    protected MemberRepository memberRepository;

    @Mock
    protected SaleRepository saleRepository;

    @InjectMocks
    protected BusinessService businessService;

    protected Business business;

    protected Business business2;

    @BeforeEach
    public void setup() {

        business = registerBusiness(1L, 1L, "홍길동",
                1L, "짱구빌라", "경기도", 52,  Category.MONTHLY, 5000, 50, 50, "역세권", SaleStatus.Y,
                5000, BusinessStatus.ING, LocalDateTime.now(), null);

        business2 = registerBusiness(2L, 1L, "성춘향",
                2L, "와르르멘션", "서울특별시 마포구", 22, Category.MONTHLY, 40, 10, 10, "역세권, 남향", SaleStatus.N,
                4000, BusinessStatus.ING, LocalDateTime.now(), null);
    }

    private Business registerBusiness(Long businessNo, Long userNo, String name,
                                      Long saleNo, String saleName, String saleLocation, Integer area, Category category, Integer salePrice,
                                      Integer depositPrice, Integer rentPrice, String description, SaleStatus saleStatus,
                                      int totalPrice, BusinessStatus status, LocalDateTime createdDate, LocalDateTime updatedDate) {

        Member member = new Member();
        member.setId(userNo);
        member.setName(name);

        Sale sale = new Sale();
        sale.setSaleNo(saleNo);
        sale.setUserNo(member);
        sale.setSaleName(saleName);
        sale.setSaleLocation(saleLocation);
        sale.setArea(area);
        sale.setCategory(category);
        sale.setSalePrice(salePrice);
        sale.setDepositPrice(depositPrice);
        sale.setRentPrice(rentPrice);
        sale.setDescription(description);
        sale.setSaleStatus(saleStatus);

        Business business = new Business();
        business.setBusinessNo(businessNo);
        business.setUserNo(member);
        business.setSaleNo(sale);
        business.setTotalPrice(totalPrice);
        business.setStatus(status);
        business.setCreatedDate(createdDate);
        business.setUpdatedDate(updatedDate);

        return business;
    }
}

