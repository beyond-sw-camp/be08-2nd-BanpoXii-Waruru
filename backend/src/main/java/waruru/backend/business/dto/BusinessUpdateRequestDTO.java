package waruru.backend.business.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import lombok.*;
import waruru.backend.business.domain.BusinessStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BusinessUpdateRequestDTO {

    @Min(0)
    private int totalPrice;

//    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    private LocalDateTime updatedDate;

}