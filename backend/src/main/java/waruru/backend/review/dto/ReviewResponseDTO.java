package waruru.backend.review.dto;

import lombok.Data;
import waruru.backend.review.domain.Review;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {

    private Long reviewNo;

    private Long userNo;

    private Long saleNo;

    private String userNickname;

    private String saleName;

    private String title;

    private String content;

    private LocalDateTime registerDate;

    private LocalDateTime updateDate;

    public ReviewResponseDTO(Review review) {

        this.reviewNo = review.getReviewNo();
        this.userNo=review.getUserNo().getId();
        this.saleNo=review.getSaleNo().getSaleNo();
        this.userNickname = review.getUserNo().getNickname();
        this.saleName = review.getSaleNo().getSaleName();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.registerDate = review.getRegisterDate();
        this.updateDate = review.getUpdateDate();
    }
}