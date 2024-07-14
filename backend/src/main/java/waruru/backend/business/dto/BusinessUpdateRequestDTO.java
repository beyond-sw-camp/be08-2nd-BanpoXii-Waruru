package waruru.backend.business.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import waruru.backend.business.domain.BusinessStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BusinessUpdateRequestDTO {

    @Min(0)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

}
