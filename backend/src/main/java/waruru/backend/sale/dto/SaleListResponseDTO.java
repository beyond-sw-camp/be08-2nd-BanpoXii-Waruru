package waruru.backend.sale.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import waruru.backend.sale.domain.Category;
import waruru.backend.user.domain.User;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Getter
@Builder
public class SaleListResponseDTO {

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

    private LocalDateTime registerDate;

    public SaleListResponseDTO(Long no, User userNo, String saleName, String saleLocation, int area,
                               Category category, int salePrice, int depositPrice, int rentPrice,
                               LocalDateTime registerDate) {
        this.no = no;
        this.userNo = userNo;
        this.saleName = saleName;
        this.saleLocation = saleLocation;
        this.area = area;
        this.category = category;
        this.salePrice = salePrice;
        this.depositPrice = depositPrice;
        this.rentPrice = rentPrice;
        this.registerDate = registerDate;
    }
}
