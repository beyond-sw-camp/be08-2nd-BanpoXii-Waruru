package waruru.backend.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Id
    private Long id;

    @Indexed
    private String accessToken;

    private String refreshToken;

    private String username;

    @TimeToLive
    private Long expiration;
}
