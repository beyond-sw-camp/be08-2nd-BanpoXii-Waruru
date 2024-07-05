package waruru.backend.detail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import waruru.backend.detail.dto.DetailRegisterRequestDTO;
import waruru.backend.detail.service.DetailService;

@Tag(name = "Detail", description = "납부 내역 관리")
@RestController
@RequestMapping("/api/details")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class DetailController {

    private final DetailService detailService;

    @Operation(summary = "납부 내역을 등록하는 API")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid DetailRegisterRequestDTO detailRegisterRequestDTO) {
        detailService.registerDetail(detailRegisterRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
