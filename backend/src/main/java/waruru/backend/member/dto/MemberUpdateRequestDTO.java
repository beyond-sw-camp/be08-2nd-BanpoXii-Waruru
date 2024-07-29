package waruru.backend.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import waruru.backend.member.domain.MemberRole;

@AllArgsConstructor
@Builder
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
