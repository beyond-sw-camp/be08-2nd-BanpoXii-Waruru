package waruru.backend.detail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.detail.dto.DetailRegisterRequestDTO;
import waruru.backend.detail.dto.DetailResponseDTO;
import waruru.backend.detail.dto.DetailUpdateRequestDTO;
import waruru.backend.detail.service.DetailService;

import java.util.List;

@Tag(name = "Detail", description = "납부 내역 관리")
@RestController
@RequestMapping("/v1/api/detail")
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
    public ResponseEntity<List<DetailResponseDTO>> getAllDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<DetailResponseDTO> details = detailService.getAllDetails(page, size);
        return ResponseEntity.ok(details);
    }

    @Operation(summary = "특정 납부 내역의 정보를 반환하는 API")
    @GetMapping("/{detailNo}")
    public ResponseEntity<DetailResponseDTO> getDetailById(@PathVariable("detailNo") Long detailNo) {

        DetailResponseDTO detailResponseDTO = detailService.getDetailById(detailNo);

        return ResponseEntity.ok(detailResponseDTO);
    }

    @Operation(summary = "납부 내역을 수정하는 API")
    @PutMapping("/update/{detailNo}")
    public ResponseEntity<Void> updateDetail(@PathVariable("detailNo") Long detailNo,
                                             @RequestBody @Valid DetailUpdateRequestDTO detailUpdateRequestDTO) {

        detailService.updateDetail(detailNo, detailUpdateRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "납부 내역을 삭제하는 API")
    @DeleteMapping("/delete/{detailNo}")
    public ResponseEntity<Void> deleteDetail(@PathVariable("detailNo") Long detailNo) {

        detailService.deleteDetail(detailNo );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
