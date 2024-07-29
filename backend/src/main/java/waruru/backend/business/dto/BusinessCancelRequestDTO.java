package waruru.backend.business.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import waruru.backend.business.domain.BusinessStatus;

@Getter
@Builder
@AllArgsConstructor
public class BusinessCancelRequestDTO {

    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    public BusinessCancelRequestDTO() {

        this.status = BusinessStatus.CANCEL;
    }
}
