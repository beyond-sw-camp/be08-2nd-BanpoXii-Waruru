package waruru.backend.detail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.detail.dto.DetailDeleteRequestDTO;
import waruru.backend.detail.dto.DetailRegisterRequestDTO;
import waruru.backend.detail.dto.DetailResponseDTO;
import waruru.backend.detail.dto.DetailUpdateRequestDTO;
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
    public ResponseEntity<List<DetailResponseDTO>> getAllDetails() {
        return ResponseEntity.ok(detailService.getAllDetails());
    }

    @Operation(summary = "특정 납부 내역의 정보를 반환하는 API")
    @GetMapping("/detail/{id}")
    public ResponseEntity<DetailResponseDTO> getDetailById(@PathVariable Long id) {
        DetailResponseDTO detailResponseDTO = detailService.getDetailById(id);
        return ResponseEntity.ok(detailResponseDTO);
    }

    @Operation(summary = "납부 내역을 수정하는 API")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateDetail(@PathVariable Long id,
                                             @RequestBody @Valid DetailUpdateRequestDTO detailUpdateRequestDTO) {
        detailService.updateDetail(id, detailUpdateRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "납부 내역을 삭제하는 API")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDetail(@PathVariable("id") Long id) {
        detailService.deleteDetail(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
