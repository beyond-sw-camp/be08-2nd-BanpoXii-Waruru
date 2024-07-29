package waruru.backend.review.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import waruru.backend.member.domain.Member;
import waruru.backend.sale.domain.Sale;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_no")
    private Long reviewNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    @JsonManagedReference
    private Member userNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sale_no", nullable = false)
    @JsonManagedReference
    private Sale saleNo;

    @Column(name = "title" ,nullable = false, length = 50)
    private String title;

    @Column(name = "content" ,nullable = false, length = 500)
    private String content;

    @CreationTimestamp
    @Column(name = "registerDate" ,nullable = false)
    private LocalDateTime registerDate;

    @UpdateTimestamp
    @Column(name = "updateDate" ,nullable = true)
    private LocalDateTime updateDate;
}
