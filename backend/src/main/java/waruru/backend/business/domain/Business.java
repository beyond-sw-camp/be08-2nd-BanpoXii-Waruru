package waruru.backend.business.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.core.instrument.config.validate.Validated;
import jakarta.persistence.*;
import lombok.*;
import waruru.backend.common.domain.BaseTimeEntity;
import waruru.backend.sale.domain.Sale;
import waruru.backend.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "business")
public class Business extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "business_no")
    private Long businessNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    @JsonManagedReference
    private User userNo;

    @OneToOne
    @JoinColumn(name = "sale_no", nullable = false)
    @JsonManagedReference
    private Sale saleNo;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_status", nullable = false)
    private BusinessStatus status = BusinessStatus.ING;

    @Builder
    public Business(Long businessNo, User userNo, Sale saleNo, int totalPrice, BusinessStatus status) {
        this.businessNo = businessNo;
        this.userNo = userNo;
        this.saleNo = saleNo;
        this.totalPrice = totalPrice;
        this.status = status;
    }


}
