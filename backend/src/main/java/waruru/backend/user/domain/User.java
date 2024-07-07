package waruru.backend.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import waruru.backend.business.domain.Business;
import waruru.backend.detail.domain.Detail;
import waruru.backend.review.domain.Review;
import waruru.backend.sales.domain.Sales;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "USER")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_no")
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String email;

    @Column(name = "pw" ,nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Builder
    public User(Long id, String email, String password, String name,
                String nickname, UserRole role, UserStatus status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.status = status;
    }

        @OneToMany(mappedBy = "user")
    private List<Business> businessEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Detail> detailEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviewEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Sales> salesEntityList = new ArrayList<>();

}
