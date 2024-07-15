package waruru.backend.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import waruru.backend.user.domain.MemberRole;
import waruru.backend.user.domain.MemberStatus;

@AllArgsConstructor
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
