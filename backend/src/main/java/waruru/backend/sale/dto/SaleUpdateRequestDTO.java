package waruru.backend.sale.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.SaleStatus;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class SaleUpdateRequestDTO {

    private String saleName;

    private String saleLocation;

    private Integer area;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer salePrice;

    private Integer depositPrice;

    private Integer rentPrice;

    private String description;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;
}
