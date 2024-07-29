package waruru.backend.business.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import waruru.backend.business.domain.BusinessStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BusinessRegisterRequestDTO {

    @NotNull
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    @NotNull
    private Long userNo;

    @NotNull
    private Long saleNo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;
}
