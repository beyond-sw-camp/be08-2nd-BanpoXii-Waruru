package waruru.backend.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "USER")
public class UserEntity {

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

    @OneToMany(mappedBy = "user")
    private List<BusinessEntity> businessEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DetailEntity> detailEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private List<SalesEntity> salesEntityList = new ArrayList<>();

}
