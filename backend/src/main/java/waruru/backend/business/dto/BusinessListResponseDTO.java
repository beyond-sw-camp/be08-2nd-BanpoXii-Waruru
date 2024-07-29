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

import java.time.LocalDateTime;

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
    private Integer area;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer salePrice;

    private Integer depositPrice;

    private Integer rentPrice;

    private String description;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public BusinessListResponseDTO(Long businessNo, int totalPrice, BusinessStatus status, Long userNo, String name, Long saleNo, String saleName, String saleLocation, Integer area, Category category, Integer salePrice, Integer depositPrice, Integer rentPrice, String description, SaleStatus saleStatus, LocalDateTime createdDate, LocalDateTime updatedDate) {

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
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}