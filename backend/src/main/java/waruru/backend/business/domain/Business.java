package waruru.backend.business.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import waruru.backend.business.dto.BusinessUpdateRequestDTO;
import waruru.backend.common.domain.BaseTimeEntity;
import waruru.backend.member.domain.Member;
import waruru.backend.sale.domain.Sale;

import java.time.LocalDateTime;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "business")
@Data
public class Business extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_no")
    private Long businessNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    @JsonManagedReference
    private Member userNo;

    @OneToOne
    @JoinColumn(name = "sale_no", nullable = false)
    @JsonManagedReference
    private Sale saleNo;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_status", nullable = false)
    private BusinessStatus status = BusinessStatus.ING;

    @Column(name = "registerDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updateDate", nullable = true)
    private LocalDateTime updatedDate = null;

    @Builder
    public Business(Long businessNo, Member userNo, Sale saleNo, int totalPrice, BusinessStatus status, LocalDateTime createdDate, LocalDateTime updateDate) {

        this.businessNo = businessNo;
        this.userNo = userNo;
        this.saleNo = saleNo;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updateDate;
    }

    public void update(BusinessUpdateRequestDTO businessUpdateRequestDTO) {

        Optional.of(businessUpdateRequestDTO.getTotalPrice()).ifPresent(totalPrice -> this.totalPrice = totalPrice);
        Optional.ofNullable(businessUpdateRequestDTO.getStatus()).ifPresent(status -> this.status = status);
        Optional.ofNullable(businessUpdateRequestDTO.getUpdatedDate()).ifPresent(updatedDate -> this.updatedDate = updatedDate);
    }
}
