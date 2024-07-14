package waruru.backend.detail.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaleRequestDTO {

    @NotNull
    private Long saleNo;
}
