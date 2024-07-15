package waruru.backend.sale.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import waruru.backend.sale.domain.Category;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
public class SaleListResponseDTO {

//    private Long no;
//
    private Long userNo;
//    private String userName;

    private String saleName;

    private String saleLocation;

    private Integer area;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer salePrice;

    private Integer depositPrice;

    private Integer rentPrice;

    private LocalDateTime registerDate;
}
