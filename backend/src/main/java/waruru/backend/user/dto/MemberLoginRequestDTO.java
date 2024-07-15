package waruru.backend.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import waruru.backend.user.domain.MemberRole;
import waruru.backend.user.domain.MemberStatus;

@Getter
@AllArgsConstructor
public class MemberLoginRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
