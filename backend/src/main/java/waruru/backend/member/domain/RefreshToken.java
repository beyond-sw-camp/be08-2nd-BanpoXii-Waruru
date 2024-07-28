package waruru.backend.member.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "RefreshToken", timeToLive = 86400)
public class RefreshToken {

    @NotNull
    @Id
    private Long id;

    @NotNull
    @Indexed
    private String accessToken;

    @NotNull
    private String refreshToken;

    @NotNull
    private String username;

    @NotNull
    @TimeToLive
    private Long expiration;
}
