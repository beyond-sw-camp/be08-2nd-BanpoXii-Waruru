package waruru.backend.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@RedisHash(value = "Email", timeToLive = 30)
public class Email {

    @Id
    private Long id;

    @Indexed
    private String email;

    private String verificationCode;

}
