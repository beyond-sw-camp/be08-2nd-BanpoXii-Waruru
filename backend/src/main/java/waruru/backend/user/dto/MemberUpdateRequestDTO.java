package waruru.backend.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import waruru.backend.user.domain.MemberRole;
import waruru.backend.user.domain.MemberStatus;

@AllArgsConstructor
@Getter
public class MemberUpdateRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    private MemberRole role;

}
