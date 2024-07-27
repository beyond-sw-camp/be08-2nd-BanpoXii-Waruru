package waruru.backend.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

//@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "jwt_token")
public class RefreshToken {

//    @Id
//    private String id;

    @NotNull
    @Id
    private String accessToken;

    @NotNull
    private String refreshToken;

    @NotNull
    private String username;

    @NotNull
    @TimeToLive
    private Long expiration;
}
