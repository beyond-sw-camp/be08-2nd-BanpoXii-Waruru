package waruru.backend.detail.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = lombok.AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
public class DetailRegisterRequestDTO {

    @NotNull
    private Long saleNo;

    @NotNull
    private Long userNo;

    @NotBlank
    private String title;

    @NotBlank
    private String category;

    @NotBlank
    private String description;

    @NotNull
    @Min(value = 0, message = "price must be greater than or equal to 0")
    private int price;

    @NotBlank
    private String detailDate;
}
