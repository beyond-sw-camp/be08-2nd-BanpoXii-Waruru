package waruru.backend.sale.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import waruru.backend.member.domain.Member;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_no")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    @JsonManagedReference
    @JsonIgnore
    private Member userNo;

    @Column(nullable = false)
    private String saleName;

    @Column(nullable = false)
    private String saleLocation;

    @Column(nullable = false)
    @NotNull
    private Integer area;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    private Integer salePrice;

    @Column
    private Integer depositPrice;

    @Column
    private Integer rentPrice;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Column
    private Integer reviewCount;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime registerDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;
//
//    public Sale(Long no, User userNo, String saleName, String saleLocation, Integer area, Category category,
//                Integer salePrice, Integer depositPrice, Integer rentPrice, String description, SaleStatus saleStatus,
//                int reviewCount, LocalDateTime registerDate, LocalDateTime updateDate) {
//        this.no = no;
//        this.userNo = userNo;
//        this.saleName = saleName;
//        this.saleLocation = saleLocation;
//        this.area = area;
//        this.category = category;
//        this.salePrice = salePrice;
//        this.depositPrice = depositPrice;
//        this.rentPrice = rentPrice;
//        this.description = description;
//        this.saleStatus = saleStatus;
//        this.reviewCount = reviewCount;
//        this.registerDate = registerDate;
//        this.updateDate = updateDate;
//    }
}
