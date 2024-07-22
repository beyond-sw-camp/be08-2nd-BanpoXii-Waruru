package waruru.backend.business.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@RequestMapping("api/business")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BusinessController {

    private final BusinessService businessService;

    @Operation(summary = "거래 내역 조회 API")
    @GetMapping("/business/{business_no}")
    public ResponseEntity<BusinessResponseDTO> search(@PathVariable long business_no) {
        BusinessResponseDTO businessResponseDTO = businessService.findBusinessByBusinessNo(business_no);
        return ResponseEntity.ok(businessResponseDTO);
    }

    @Operation(summary = "사용자의 모든 거래 내역 조회")
    @GetMapping("/business/list/{user_no}") //OK
    public ResponseEntity<List<BusinessListResponseDTO>> findAllList(@PathVariable long user_no) {
        List<BusinessListResponseDTO> lists = businessService.findAllList(user_no);
        return ResponseEntity.ok(lists);
    }

    @Operation(summary = "거래 내역 등록 API")
    @PostMapping("/register")
    public ResponseEntity<Void> register(BusinessRegisterRequestDTO businessRegisterRequestDTO) {
        businessService.registerBusiness(businessRegisterRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "거래 내역 수정 API")
    @PutMapping("/update/{business_no}")
    public ResponseEntity<Void> update(@PathVariable long business_no, @RequestBody BusinessUpdateRequestDTO businessUpdateRequestDTO) {
        businessService.updateBusiness(business_no, businessUpdateRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "거래 내역 취소 API")
    @PutMapping("/cancel/{business_no}")
    public ResponseEntity<Void> cancel(@PathVariable long business_no, @RequestBody BusinessCancelRequestDTO businessCancelRequestDTO) {
        businessService.cancelBusiness(business_no, businessCancelRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "거래 내역 삭제 API")
    @DeleteMapping("/delete/{business_no}")
    public ResponseEntity<Void> delete(@PathVariable long business_no) {
        businessService.deleteBusiness(business_no);
        return ResponseEntity.noContent().build();
    }

}
