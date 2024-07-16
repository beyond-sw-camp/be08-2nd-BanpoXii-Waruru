package waruru.backend.business.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleStatus;
import waruru.backend.user.domain.User;


@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BusinessRegisterRequestDTO {

    // Business Table Field
    @Min(0)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    // User Table Field
    @NotNull
    private Long userNo;

    @NotBlank
    private String name;

    // Sale Table Field
    @NotNull
    private Long saleNo;

    @NotBlank
    private String saleName;

    @NotBlank
    private String saleLocation;

    @Min(0)
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

}
