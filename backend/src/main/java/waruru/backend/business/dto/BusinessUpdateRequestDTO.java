package waruru.backend.business.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import waruru.backend.business.domain.BusinessStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class BusinessUpdateRequestDTO {

    @Min(0)
    private int totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    private LocalDateTime updatedDate;
}