package waruru.backend.business.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.business.domain.Business;
import waruru.backend.business.dto.*;
import waruru.backend.business.service.BusinessService;

@Tag(name = "Business", description = "거래 내역 관리")
@RestController
@RequestMapping("api/business")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BusinessController {

    private final BusinessService businessService;

    @Operation(summary = "거래 내역 조회 API")
    @GetMapping("/list/{user_id}")
    public ResponseEntity<BusinessRequestDTO> search( @PathVariable long user_id, @RequestBody BusinessRequestDTO businessRequestDTO) {
        businessService.findBusinessByBusinessNo(user_id, businessRequestDTO);
        return ResponseEntity.ok(businessRequestDTO);
    }

    @Operation(summary = "거래 내역 등록 API")
    @PostMapping("/register/{user_id}")
    public ResponseEntity<Void> register(@PathVariable long user_id, @RequestBody BusinessRegisterRequestDTO businessRegisterRequestDTO) {
        businessService.registerBusiness(user_id, businessRegisterRequestDTO);
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
    @DeleteMapping("delete/{business_no}")
    public ResponseEntity<Void> delete(@PathVariable long business_no) {
        businessService.deleteBusiness(business_no);
        return ResponseEntity.noContent().build();
    }

}