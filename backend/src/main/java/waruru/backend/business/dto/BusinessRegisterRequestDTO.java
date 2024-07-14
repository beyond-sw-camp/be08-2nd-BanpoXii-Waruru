package waruru.backend.business.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleStatus;
import waruru.backend.user.domain.User;


@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BusinessRegisterRequestDTO {

    // Business Table Field
    @NotBlank
    private Long businessNo;

    @Min(0)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    // User Table Field
    @NotBlank
    private Long userId;

    @NotBlank
    private String name;

    // Sale Table Field
    @NotBlank
    private Long saleNo;

    @NotBlank
    private String saleName;

    @NotBlank
    private String saleLocation;

    @NotBlank
    private int area;

    @Enumerated(EnumType.STRING)
    private Category category;

    @NotBlank
    private int salePrice;

    @NotBlank
    private int depositPrice;

    @NotBlank
    private int rentPrice;

    private String description;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

}
