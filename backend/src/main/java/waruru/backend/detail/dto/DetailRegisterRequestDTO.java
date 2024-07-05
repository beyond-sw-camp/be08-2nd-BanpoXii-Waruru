package waruru.backend.detail.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import waruru.backend.sales.domain.Sales;
import waruru.backend.user.domain.UserEntity;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class DetailRegisterRequestDTO {

    @NotBlank
    Sales saleNo;

    @NotBlank
    UserEntity userNo;

    @NotBlank
    String title;

    @NotBlank
    String category;

    @NotBlank
    String description;

    @NotNull
    @Min(value = 0, message = "price must be greate than or equal to 0")
    int price;

    @NotBlank
    String detailDate;

    LocalDateTime registrationDate;

    LocalDateTime updatedDate;
}
