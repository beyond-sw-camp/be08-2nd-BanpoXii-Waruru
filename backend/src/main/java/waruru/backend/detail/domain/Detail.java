package waruru.backend.detail.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import waruru.backend.sales.domain.Sales;
import waruru.backend.user.domain.UserEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_no", nullable = false)
    @JsonManagedReference
    private Sales saleNo;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    @JsonManagedReference
    private UserEntity userNo;

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

    @CreationTimestamp
    private LocalDateTime registrationDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Builder
    public Detail(Sales saleNo, UserEntity userNo, String title, String category, String description, int price, String detailDate) {
        this.saleNo = saleNo;
        this.userNo = userNo;
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
        this.detailDate = detailDate;
    }
}
