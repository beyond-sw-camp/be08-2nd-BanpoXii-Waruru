package waruru.backend.detail.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class DetailUpdateRequestDTO {

    private String title;

    private String category;

    private String description;

    @Min(0)
    private int price;

    private String detailDate;
}
