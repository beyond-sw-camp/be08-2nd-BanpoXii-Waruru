package waruru.backend.sale.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleStatus;
import waruru.backend.user.domain.User;

import java.time.LocalDateTime;

public class SaleResponseDTO {

    private Long no;

    private User userNo;

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


    private LocalDateTime registerDate;

    private LocalDateTime updateDate;

    public SaleResponseDTO(Long no, User userNo, String saleName, String saleLocation, int area,
                           Category category, int salePrice, int depositPrice, int rentPrice, String description,
                           SaleStatus saleStatus, LocalDateTime registerDate, LocalDateTime updateDate) {
        this.no = no;
        this.userNo = userNo;
        this.saleName = saleName;
        this.saleLocation = saleLocation;
        this.area = area;
        this.category = category;
        this.salePrice = salePrice;
        this.depositPrice = depositPrice;
        this.rentPrice = rentPrice;
        this.description = description;
        this.saleStatus = saleStatus;
        this.registerDate = registerDate;
        this.updateDate = updateDate;
    }

    public static SaleResponseDTO from(Sale sale) {
        return new SaleResponseDTO(
                sale.getNo(),
                sale.getUserNo(),
                sale.getSaleName(),
                sale.getSaleLocation(),
                sale.getArea(),
                sale.getCategory(),
                sale.getSalePrice(),
                sale.getDepositPrice(),
                sale.getRentPrice(),
                sale.getDescription(),
                sale.getSaleStatus(),
                sale.getRegisterDate(),
                sale.getUpdateDate()
        );
    }
}
