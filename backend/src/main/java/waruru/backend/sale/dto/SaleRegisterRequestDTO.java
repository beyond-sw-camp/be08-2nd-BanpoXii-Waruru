package waruru.backend.sale.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.SaleStatus;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaleRegisterRequestDTO {

    private Long userNo;

    private String saleName;

    private String saleLocation;

    private Integer area;

    private Category category;

    private Integer salePrice;

    private Integer depositPrice;

    private Integer rentPrice;

    private String description;

    private SaleStatus saleStatus;
}

