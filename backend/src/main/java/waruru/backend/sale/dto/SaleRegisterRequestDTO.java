package waruru.backend.sale.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.SaleStatus;
import waruru.backend.member.domain.Member;

import java.time.LocalDateTime;


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

    private LocalDateTime registerDate;

    private LocalDateTime updateDate;
}
