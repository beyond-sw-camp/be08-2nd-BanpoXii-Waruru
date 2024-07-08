package waruru.backend.sale.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import waruru.backend.user.domain.User;

import java.time.LocalDateTime;

@NoArgsConstructor
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
    private User userNo;

    @Column(nullable = false)
    private String saleName;

    @Column(nullable = false)
    private String saleLocation;

    @Column(nullable = false)
    private int area;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    private int salePrice;

    @Column
    private int depositPrice;

    @Column
    private int rentPrice;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Column
    private int reviewCount;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime registerDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    public Sale(Long no, User userNo, String saleName, String saleLocation, int area, Category category,
                int salePrice, int depositPrice, int rentPrice, String description, SaleStatus saleStatus,
                int reviewCount, LocalDateTime registerDate, LocalDateTime updateDate) {
        this.no = no;
        this.userNo = userNo;
        this.saleName = saleName;
        this.saleLocation = saleLocation;
        this.area = area;
        this.category = category;
        this.salePrice = salePrice;
        this.depositPrice = depositPrice;
        this.rentPrice = rentPrice;
        this.description = description;
        this.saleStatus = saleStatus;
        this.reviewCount = reviewCount;
        this.registerDate = registerDate;
        this.updateDate = updateDate;
    }
}
