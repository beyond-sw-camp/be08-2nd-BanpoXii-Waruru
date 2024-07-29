package waruru.backend.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import waruru.backend.member.domain.MemberRole;
import waruru.backend.member.domain.MemberStatus;

@AllArgsConstructor
@Builder
@Getter
public class MemberRegisterRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    private MemberRole role;

    @NotNull
    private MemberStatus status;
}
