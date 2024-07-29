package waruru.backend.detail.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.detail.domain.Detail;
import waruru.backend.detail.domain.DetailRepository;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.member.domain.MemberRole;
import waruru.backend.member.domain.MemberStatus;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.sale.domain.SaleStatus;

@ExtendWith(MockitoExtension.class)
public abstract class DetailCommonSetUp {

    @Mock
    protected DetailRepository detailRepository;

    @Mock
    protected MemberRepository memberRepository;

    @Mock
    protected SaleRepository saleRepository;

    @InjectMocks
    protected DetailService detailService;

    protected Detail detail;
    protected Detail detail2;
    protected Member member;
    protected Sale sale;

    @BeforeEach
    public void setUp() {

        detail = registDetail(1L, 1L, 1L, "test", "test", "test", 100, "2024-07-21");
        detail2 = registDetail(2L, 2L, 2L, "test2", "test2", "test2", 200, "2024-07-22");

        member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password("1234")
                .name("test")
                .nickname("test")
                .role(MemberRole.LESSEE)
                .status(MemberStatus.Y)
                .business(null)
                .details(null)
                .reviews(null)
                .sales(null)
                .build();

        sale = Sale.builder()
                .saleNo(1L)
                .userNo(member)
                .saleName("test")
                .saleLocation("test")
                .area(10)
                .category(Category.JEONSE)
                .salePrice(10)
                .depositPrice(10)
                .rentPrice(10)
                .description("test")
                .saleStatus(SaleStatus.N)
                .reviewCount(10)
                .build();
    }

    private Detail registDetail(Long detailNo, Long userId, Long saleNo, String title, String category, String description, int price, String detailDate) {

        Member member = new Member();
        member.setId(userId);

        Sale sale = new Sale();
        sale.setSaleNo(saleNo);

        Detail detail = Detail.builder()
                .detailNo(detailNo)
                .userNo(member)
                .saleNo(sale)
                .title(title)
                .category(category)
                .description(description)
                .price(price)
                .detailDate(detailDate)
                .build();

        return detail;
    }
}
