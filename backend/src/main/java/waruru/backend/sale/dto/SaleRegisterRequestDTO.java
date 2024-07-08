package waruru.backend.sale.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.SaleStatus;
import waruru.backend.user.domain.User;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SaleRegisterRequestDTO {

    @NotBlank
    private User userNo;

    @NotBlank
    private String saleName;

    @NotBlank
    private String saleLocation;

    @NotBlank
    private int area;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Min(0)
    private int salePrice;

    @Min(0)
    private int depositPrice;

    @Min(0)
    private int rentPrice;

    private String description;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;


    private LocalDateTime registerDate;

    private LocalDateTime updateDate;
}
