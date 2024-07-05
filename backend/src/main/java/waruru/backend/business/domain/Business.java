package waruru.backend.business.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessNo;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    @JsonManagedReference
    private User userNo;

    @OneToOne
    @JoinColumn(name = "sale_no", nullable = false)
    @JsonManagedReference
    private Sale saleNo;

    @Column(nullable = false)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String status;

    @CreationTimestamp
    private LocalDateTime registerDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Builder
    public Business(Long businessNo, User userNo, Sale saleNo, int total_price, String status, LocalDateTime registerDate, LocalDateTime updateDate) {
        this.businessNo = businessNo;
        this.userNo = userNo;
        this.saleNo = saleNo;
        this.totalPrice = total_price;
        this.status = status;
        this.registerDate = registerDate;
        this.updateDate = updateDate;
    }
}
