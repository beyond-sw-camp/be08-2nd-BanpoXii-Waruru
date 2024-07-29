package waruru.backend.sale.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import waruru.backend.common.domain.BaseTimeEntity;
import waruru.backend.member.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
public class Sale extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_no")
    private Long saleNo;

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

    @Column(nullable = false, length = 500)
    @Size(max = 500)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @ColumnDefault("0")
    private Integer reviewCount;
}

