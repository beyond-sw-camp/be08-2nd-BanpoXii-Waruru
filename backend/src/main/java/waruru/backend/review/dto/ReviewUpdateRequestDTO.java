package waruru.backend.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReviewUpdateRequestDTO {

    private String title;

    private String content;

    private LocalDateTime updateDate;

    private Long userNo;
}
