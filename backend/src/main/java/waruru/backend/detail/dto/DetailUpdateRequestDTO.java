package waruru.backend.detail.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class DetailUpdateRequestDTO {

    @NotNull
    @Min(0)
    private Long id;

    private String title;

    private String category;

    private String description;

    @Min(0)
    private int price;

    private String detailDate;
}
