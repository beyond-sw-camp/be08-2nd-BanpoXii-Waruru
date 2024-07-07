package waruru.backend.detail.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class DetailDeleteRequestDTO {

    @NotBlank
    private String detailTitle;
}
