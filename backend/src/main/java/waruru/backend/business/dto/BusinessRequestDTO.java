package waruru.backend.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.sale.domain.Category;
import waruru.backend.sale.domain.SaleStatus;
import waruru.backend.user.domain.User;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessRequestDTO {

    // User Table Field
    @NotBlank
    private Long userNo;

    // Sale Table Field
    @NotBlank
    private Long saleNo;
}
