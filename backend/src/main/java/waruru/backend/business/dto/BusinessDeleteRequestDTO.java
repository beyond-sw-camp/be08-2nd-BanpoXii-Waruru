package waruru.backend.business.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BusinessDeleteRequestDTO {

    @NotBlank
    private Long businessNo;

    @NotBlank
    private Long userNo;

    @NotBlank
    private Long saleNo;

}
