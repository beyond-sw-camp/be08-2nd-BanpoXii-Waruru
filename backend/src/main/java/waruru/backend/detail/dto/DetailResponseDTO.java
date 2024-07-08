package waruru.backend.detail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import waruru.backend.detail.domain.Detail;
import waruru.backend.sale.domain.Sale;
import waruru.backend.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DetailResponseDTO {

    private Long id;
    private Sale saleNo;
    private User userNo;
    private String title;
    private String category;
    private String description;
    private int pricce;
    private String detailDate;
    private LocalDateTime registrationDate;
    private LocalDateTime updatedDate;

    public static List<DetailResponseDTO> listOf(List<Detail> filtereddetails) {
        return filtereddetails.stream()
                .map(DetailResponseDTO::of)
                .toList();
    }

    private static DetailResponseDTO of(Detail detail) {

        return new DetailResponseDTO(

                detail.getId(),
                detail.getSaleNo(),
                detail.getUserNo(),
                detail.getTitle(),
                detail.getCategory(),
                detail.getDescription(),
                detail.getPrice(),
                detail.getDetailDate(),
                detail.getRegistrationDate(),
                detail.getUpdateDate()
        );
    }
}
