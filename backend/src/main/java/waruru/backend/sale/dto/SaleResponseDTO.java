package waruru.backend.sale.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class SaleResponseDTO {

    private Long saleNo;

    private String nickname;

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

    private Integer reviewCount;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public SaleResponseDTO(Sale sale) {

        this.saleNo = sale.getSaleNo();
        this.nickname = sale.getUserNo().getNickname();
        this.saleName = sale.getSaleName();
        this.saleLocation = sale.getSaleLocation();
        this.area = sale.getArea();
        this.category = sale.getCategory();
        this.salePrice = sale.getSalePrice();
        this.depositPrice = sale.getDepositPrice();
        this.rentPrice = sale.getRentPrice();
        this.description = sale.getDescription();
        this.saleStatus = sale.getSaleStatus();
        this.reviewCount = sale.getReviewCount();
        this.createdDate = sale.getCreatedDate();
        this.updatedDate = sale.getUpdatedDate();
    }
}

