package waruru.backend.detail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.detail.domain.Detail;
import waruru.backend.detail.dto.DetailRegisterRequestDTO;
import waruru.backend.detail.service.DetailService;

import java.util.List;

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

    @Operation(summary = "모든 납부 내역의 리스트를 반환하는 API")
    @GetMapping("/list")
    public ResponseEntity<List<Detail>> getAllDetails() {
        List<Detail> details = detailService.getAllDetails();
        return ResponseEntity.ok(details);
    }

    @Operation(summary = "특정 납부 내역의 정보를 반환하는 API")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Detail> getDetailById(@PathVariable Long id) {
        Detail detail = detailService.getDetailById(id);
        return ResponseEntity.ok(detail);
    }
}
