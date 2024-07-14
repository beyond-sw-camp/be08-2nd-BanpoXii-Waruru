package waruru.backend.detail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import waruru.backend.detail.domain.Detail;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DetailResponseDTO {

    private Long id;
    private String title;
    private String category;
    private String description;
    private int pricce;
    private String detailDate;

    public static List<DetailResponseDTO> listOf(List<Detail> filtereddetails) {
        return filtereddetails.stream()
                .map(DetailResponseDTO::of)
                .toList();
    }

    public static DetailResponseDTO of(Detail detail) {

        return new DetailResponseDTO(

                detail.getId(),
                detail.getTitle(),
                detail.getCategory(),
                detail.getDescription(),
                detail.getPrice(),
                detail.getDetailDate()
        );
    }
}
