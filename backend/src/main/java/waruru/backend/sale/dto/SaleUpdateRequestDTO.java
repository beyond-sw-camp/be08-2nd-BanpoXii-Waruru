package waruru.backend.sale.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.SaleStatus;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SaleUpdateRequestDTO {
    private String saleName;

    private String saleLocation;

    private int area;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int salePrice;

    private int depositPrice;

    private int rentPrice;

    private String description;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

}