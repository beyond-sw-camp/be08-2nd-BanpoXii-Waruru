package waruru.backend.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.member.domain.Member;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessResponseDTO {

    // Business Table Field
    @NotBlank
    private Long businessNo;

    @Min(0)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    // User Table Field
    @NotBlank
    private Long userNo;

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

    public BusinessResponseDTO(Business business, Member member, Sale sale) {
        this.businessNo = business.getBusinessNo();
        this.totalPrice = business.getTotalPrice();
        this.status = business.getStatus();
        this.userNo = member.getId();
        this.name = member.getName();
        this.saleNo = sale.getNo();
        this.saleName = sale.getSaleName();
        this.saleLocation = sale.getSaleLocation();
        this.area = sale.getArea();
        this.category = sale.getCategory();
        this.salePrice = sale.getSalePrice();
        this.depositPrice = sale.getDepositPrice();
        this.rentPrice = sale.getRentPrice();
        this.description = sale.getDescription();
        this.saleStatus = sale.getSaleStatus();
    }

}
