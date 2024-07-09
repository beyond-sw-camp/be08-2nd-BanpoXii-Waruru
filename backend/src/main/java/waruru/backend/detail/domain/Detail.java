package waruru.backend.detail.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import waruru.backend.common.domain.BaseTimeEntity;
import waruru.backend.detail.dto.DetailUpdateRequestDTO;
import waruru.backend.sale.domain.Sale;
import waruru.backend.user.domain.User;

import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Detail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_no")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sale_no", nullable = false)
    @JsonManagedReference
    private Sale saleNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    @JsonManagedReference
    private User userNo;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String detailDate;

    @Builder
    public Detail(Sale saleNo, User userNo, String title, String category, String description, int price, String detailDate) {
        this.saleNo = saleNo;
        this.userNo = userNo;
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
        this.detailDate = detailDate;
    }

    public void update(DetailUpdateRequestDTO detailUpdateRequestDTO) {
        Optional.ofNullable(detailUpdateRequestDTO.getTitle()).ifPresent(title -> this.title = title);
        Optional.ofNullable(detailUpdateRequestDTO.getCategory()).ifPresent(category -> this.category = category);
        Optional.ofNullable(detailUpdateRequestDTO.getDescription()).ifPresent(description -> this.description = description);
        Optional.ofNullable(detailUpdateRequestDTO.getPrice()).ifPresent(price -> this.price = price);
        Optional.ofNullable(detailUpdateRequestDTO.getDetailDate()).ifPresent(detailDate -> this.detailDate = detailDate);
    }
}
