package waruru.backend.detail.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import waruru.backend.sale.domain.Sale;
import waruru.backend.user.domain.User;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class DetailRegisterRequestDTO {

    @NotBlank
    Sale saleNo;

    @NotBlank
    User userNo;

    @NotBlank
    String title;

    @NotBlank
    String category;

    @NotBlank
    String description;

    @NotNull
    @Min(value = 0, message = "price must be greater than or equal to 0")
    int price;

    @NotBlank
    String detailDate;
}
