package waruru.backend.detail.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class DetailDeleteRequestDTO {

    @NotNull
    private Long detailNo;
}
