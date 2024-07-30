package waruru.backend.reviewtest.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.review.domain.Review;
import waruru.backend.review.domain.ReviewRepository;
import waruru.backend.review.service.ReviewService;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public abstract class ReviewCommonSetUp {

    @Mock
    protected ReviewRepository reviewRepository;

    @Mock
    protected MemberRepository memberRepository;

    @Mock
    protected SaleRepository saleRepository;

    @InjectMocks
    protected ReviewService reviewService;

    protected Review review;
    protected Review review2;

    @BeforeEach
    public void setUp() {

        review = createReview(1L, "1번 후기 Title Test", "1번 후기 Content Test", 1L,"하우스", 1L, "1번 집",LocalDateTime.now(),null);
        review2 = createReview(2L, "2번 후기 Title Test", "2번 후기 Content Test", 2L,"하우스키퍼", 2L, "2번 집",LocalDateTime.now(),null);
    }

    private Review createReview(Long reviewNo, String title, String content, Long userId, String uNickname, Long saleNo, String setSaleName,LocalDateTime registerDate, LocalDateTime updateDate) {

        Member member = new Member();
        member.setId(userId);
        member.setNickname(uNickname);

        Sale sale = new Sale();
        sale.setSaleNo(saleNo);
        sale.setSaleName(setSaleName);

        Review review = new Review();
        review.setReviewNo(reviewNo);
        review.setTitle(title);
        review.setContent(content);
        review.setUserNo(member);
        review.setSaleNo(sale);
        review.setRegisterDate(registerDate);
        review.setUpdateDate(updateDate);

        return review;
    }
}
