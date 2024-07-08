package waruru.backend.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewRequestDTO {

    private Long userNo;

    private Long saleNo;

    private String title;

    private String content;

    private LocalDateTime registerDate;
}