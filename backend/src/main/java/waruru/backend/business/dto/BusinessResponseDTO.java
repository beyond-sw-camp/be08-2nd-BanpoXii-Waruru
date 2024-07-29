package waruru.backend.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.SaleStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessResponseDTO {

    @NotBlank
    private Long businessNo;

    @Min(0)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    @NotNull
    private Long userNo;

    @NotNull
    private String name;

    @NotNull
    private Long saleNo;

    @NotNull
    private String saleName;

    @NotNull
    private String saleLocation;

    @Min(0)
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

    public static BusinessResponseDTO of(Business business) {

        return new BusinessResponseDTO(

                business.getBusinessNo(),
                business.getTotalPrice(),
                business.getStatus(),
                business.getUserNo().getId(),
                business.getUserNo().getName(),
                business.getSaleNo().getSaleNo(),
                business.getSaleNo().getSaleName(),
                business.getSaleNo().getSaleLocation(),
                business.getSaleNo().getArea(),
                business.getSaleNo().getCategory(),
                business.getSaleNo().getSalePrice(),
                business.getSaleNo().getRentPrice(),
                business.getSaleNo().getDepositPrice(),
                business.getSaleNo().getDescription(),
                business.getSaleNo().getSaleStatus(),
                business.getCreatedDate(),
                business.getUpdatedDate()
        );
    }
}

