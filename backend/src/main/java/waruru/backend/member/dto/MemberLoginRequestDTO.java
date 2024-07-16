package waruru.backend.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
