package waruru.backend.review.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewRequestDTO {

    private Long userNo;

    private Long saleNo;

    private String title;

    private String content;

    private LocalDateTime registerDate;
}