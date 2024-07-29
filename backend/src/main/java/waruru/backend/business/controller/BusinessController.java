package waruru.backend.business.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.business.dto.*;
import waruru.backend.business.service.BusinessService;

import java.util.List;

@Tag(name = "Business", description = "거래 내역 관리")
@RestController
@RequestMapping("/v1/api/business")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BusinessController {

    private final BusinessService businessService;

    @Operation(summary = "거래 내역을 등록하는 API")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody BusinessRegisterRequestDTO businessRegisterRequestDTO) {

        businessService.registerBusiness(businessRegisterRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "사용자의 모든 거래 내역의 리스트를 반환하는 API")
    @GetMapping("/list/{userNo}")
    public ResponseEntity<List<BusinessListResponseDTO>> findAllList(@PathVariable long userNo) {

        List<BusinessListResponseDTO> lists = businessService.findAllList(userNo);

        return ResponseEntity.ok(lists);
    }

    @Operation(summary = "특정 거래 내역의 정보를 반환하는 API")
    @GetMapping("/{businessNo}")
    public ResponseEntity<BusinessResponseDTO> search(@PathVariable long businessNo) {

        BusinessResponseDTO businessResponseDTO = businessService.findBusinessByBusinessNo(businessNo);

        return ResponseEntity.ok(businessResponseDTO);
    }

    @Operation(summary = "거래 내역을 수정하는 API")
    @PutMapping("/update/{businessNo}")
    public ResponseEntity<Void> update(@PathVariable("businessNo") Long businessNo,
                                       @RequestBody BusinessUpdateRequestDTO businessUpdateRequestDTO) {

        businessService.updateBusiness(businessNo, businessUpdateRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "거래를 취소하는 API")
    @PutMapping("/cancel/{businessNo}")
    public ResponseEntity<Void> cancel(@PathVariable long businessNo, @RequestBody BusinessCancelRequestDTO businessCancelRequestDTO) {

        businessService.cancelBusiness(businessNo, businessCancelRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "거래 내역을 삭제하는 API")
    @DeleteMapping("/delete/{businessNo}")
    public ResponseEntity<Void> delete(@PathVariable long businessNo) {

        businessService.deleteBusiness(businessNo);

        return ResponseEntity.noContent().build();
    }
}
