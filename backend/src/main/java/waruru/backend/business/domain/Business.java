package waruru.backend.business.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import waruru.backend.sale.domain.Sale;
import waruru.backend.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_no")
    private Long businessNo;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    @JsonManagedReference
    private User userNo;

    @OneToOne
    @JoinColumn(name = "sale_no", nullable = false)
    @JsonManagedReference
    private Sale saleNo;

    @Column(name = "totla_price", nullable = false)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_status", nullable = false)
    private BusinessStatus status;

    @CreationTimestamp
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Builder
    public Business(Long businessNo, User userNo, Sale saleNo, int total_price, BusinessStatus status, LocalDateTime registerDate, LocalDateTime updateDate) {
        this.businessNo = businessNo;
        this.userNo = userNo;
        this.saleNo = saleNo;
        this.totalPrice = total_price;
        this.status = status;
        this.registerDate = registerDate;
        this.updateDate = updateDate;
    }
}
