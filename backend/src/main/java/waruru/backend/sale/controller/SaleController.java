package waruru.backend.sale.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.sale.dto.*;
import waruru.backend.sale.service.SaleService;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "매물 정보", description = "매물 관리를 위한 api")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping //OK
    @Operation(summary = "매물 등록", description = "/api/sales 새 매물 등록")
    public ResponseEntity<SaleResponseDTO> registerSale(@RequestBody @Valid SaleRegisterRequestDTO registerRequestDTO) {
        SaleResponseDTO saleresponseDTO = saleService.registerSale(registerRequestDTO);
        return ResponseEntity.ok(saleresponseDTO);
    }

    @GetMapping("/list") //OK
    @Operation(summary = "모든 매물 리스트 조회", description = "/api/sales/list 매물 정보 리스트 조회(특정 필드)")
    public ResponseEntity<List<SaleListResponseDTO>> findAllList() {
        List<SaleListResponseDTO> lists = saleService.findAllList();
        return ResponseEntity.ok(lists);
    }

    @GetMapping("/detail/{no}") // OK
    @Operation(summary = "상세 매물 조회", description = "/api/sales/detail/{no} 특정 매물 상세 조회(전체 필드)")
    public ResponseEntity<SaleResponseDTO> findById(@PathVariable Long no) {
        SaleResponseDTO saleResponseDTO = saleService.findById(no);
        return ResponseEntity.ok(saleResponseDTO);
    }

    @DeleteMapping("/delete/{no}") // OK
    @Operation(summary = "매물 삭제", description = "/api/sales/delete/{no} 매물 삭제")
    public ResponseEntity<Void> deleteSale(@PathVariable Long no) {
        saleService.deleteSale(no);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update/{no}") // OK
    @Operation(summary = "매물 정보 수정", description = "/api/sales/update/{no} 매물 정보 수정")
    public ResponseEntity<Void> updateSale(@PathVariable("no") Long no,
                                           @RequestBody @Valid SaleUpdateRequestDTO updateRequestDTO) {
        saleService.updateSale(no, updateRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
