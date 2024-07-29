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

    private Long detailNo;

    private String nickName;

    private String saleName;

    private String title;

    private String category;

    private String description;

    private int price;

    private String detailDate;

    public static List<DetailResponseDTO> listOf(List<Detail> filtereddetails) {
        return filtereddetails.stream()
                .map(DetailResponseDTO::of)
                .toList();
    }

    public static DetailResponseDTO of(Detail detail) {

        return new DetailResponseDTO(

                detail.getDetailNo(),
                detail.getUserNo().getNickname(),
                detail.getSaleNo().getSaleName(),
                detail.getTitle(),
                detail.getCategory(),
                detail.getDescription(),
                detail.getPrice(),
                detail.getDetailDate()
        );
    }
}
