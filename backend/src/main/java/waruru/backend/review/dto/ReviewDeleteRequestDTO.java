package waruru.backend.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDeleteRequestDTO {

    private Long reviewNo;

//    private ReviewStatus reviewStatus;
}
