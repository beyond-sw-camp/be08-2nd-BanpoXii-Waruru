package waruru.backend.business.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.SaleStatus;


@Getter
@Setter
public class BusinessListResponseDTO {

    @NotBlank
    private Long businessNo;

    @Min(0)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    @NotBlank
    private Long userNo;

    @NotBlank
    private String name;

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

    public BusinessListResponseDTO(Long businessNo, int totalPrice, BusinessStatus status, Long userNo, String name, Long saleNo, String saleName, String saleLocation, int area, Category category, int salePrice, int depositPrice, int rentPrice, String description, SaleStatus saleStatus) {
        this.businessNo = businessNo;
        this.totalPrice = totalPrice;
        this.status = status;
        this.userNo = userNo;
        this.name = name;
        this.saleNo = saleNo;
        this.saleName = saleName;
        this.saleLocation = saleLocation;
        this.area = area;
        this.category = category;
        this.salePrice = salePrice;
        this.depositPrice = depositPrice;
        this.rentPrice = rentPrice;
        this.description = description;
        this.saleStatus = saleStatus;
    }
}