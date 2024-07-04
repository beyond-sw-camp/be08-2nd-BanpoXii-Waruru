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

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_no", nullable = false)
    @JsonManagedReference
    private Sale saleNo;

    @ManyToOne
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
    private String price;

    @Column(nullable = false)
    private String detailDate;

    @CreationTimestamp
    private LocalDateTime registDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Builder
    public Detail(Sale saleNo, User userNo, String title, String category, String description, String price, String detailDate) {
        this.saleNo = saleNo;
        this.userNo = userNo;
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
        this.detailDate = detailDate;
    }
}
