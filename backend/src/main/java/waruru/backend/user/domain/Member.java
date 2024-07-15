package waruru.backend.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import waruru.backend.business.domain.Business;
import waruru.backend.detail.domain.Detail;
import waruru.backend.review.domain.Review;
import waruru.backend.sale.domain.Sale;
import waruru.backend.user.dto.MemberUpdateRequestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String email;

    @Column(name = "pw" ,nullable = false, length = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToMany(mappedBy = "userNo")
    private List<Business> business = new ArrayList<>();

    @OneToMany(mappedBy = "userNo")
    private List<Detail> details = new ArrayList<>();

    @OneToMany(mappedBy = "userNo")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "userNo")
    private List<Sale> sales = new ArrayList<>();

    public void update(MemberUpdateRequestDTO memberUpdateRequestDTO) {
        Optional.ofNullable(memberUpdateRequestDTO.getName()).ifPresent(name -> this.name = name);
        Optional.ofNullable(memberUpdateRequestDTO.getNickname()).ifPresent(nickname -> this.nickname = nickname);
        Optional.ofNullable(memberUpdateRequestDTO.getRole()).ifPresent(role -> this.role = role);
    }

    public void updateStatus() {
        this.status = MemberStatus.N;
    }
}
