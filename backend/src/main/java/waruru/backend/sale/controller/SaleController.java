package waruru.backend.sale.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.sale.dto.SaleListResponseDTO;
import waruru.backend.sale.dto.SaleRegisterRequestDTO;
import waruru.backend.sale.dto.SaleResponseDTO;
import waruru.backend.sale.dto.SaleUpdateRequestDTO;
import waruru.backend.sale.service.SaleService;

import java.util.List;

@RestController
@RequestMapping("/v1/api/sales")
@Tag(name = "Sale", description = "매물 관리")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/register")
    @Operation(summary = "매물을 등록하는 API")
    public ResponseEntity<SaleResponseDTO> registerSale(@RequestBody @Valid SaleRegisterRequestDTO registerRequestDTO) {

        SaleResponseDTO saleresponseDTO = saleService.registerSale(registerRequestDTO);

        return ResponseEntity.ok(saleresponseDTO);
    }

    @GetMapping("/list")
    @Operation(summary = "모든 매물의 리스트를 반환하는 API")
    public ResponseEntity<List<SaleListResponseDTO>> findAllList(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {

        List<SaleListResponseDTO> lists = saleService.findAllList(page, size);

        return ResponseEntity.ok(lists);
    }

    @GetMapping("/{saleNo}")
    @Operation(summary = "특정 매물의 정보를 반환하는 API")
    public ResponseEntity<SaleResponseDTO> findById(@PathVariable("saleNo") Long saleNo) {

        SaleResponseDTO saleResponseDTO = saleService.findById(saleNo);

        return ResponseEntity.ok(saleResponseDTO);
    }

    @PutMapping("/update/{saleNo}")
    @Operation(summary = "매물 정보를 수정하는 API")
    public ResponseEntity<Void> updateSale(@PathVariable("saleNo") Long saleNo,
                                           @RequestBody @Valid SaleUpdateRequestDTO updateRequestDTO) {

        saleService.updateSale(saleNo, updateRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{saleNo}")
    @Operation(summary = "매물을 삭제하는 API")
    public ResponseEntity<Void> deleteSale(@PathVariable("saleNo") Long saleNo) {

        saleService.deleteSale(saleNo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
