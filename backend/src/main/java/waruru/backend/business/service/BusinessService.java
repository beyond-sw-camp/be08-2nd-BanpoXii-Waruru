package waruru.backend.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessRepository;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.business.dto.*;
import waruru.backend.common.exception.BusinessException;
import waruru.backend.common.exception.ErrorCode;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.user.domain.User;
import waruru.backend.user.domain.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;

    public BusinessService(BusinessRepository businessRepository, UserRepository userRepository, SaleRepository saleRepository) {
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.saleRepository = saleRepository;
    }

    // 거래 내역 조회
    public BusinessResponseDTO findBusinessByBusinessNo(Long businessNo) {
        Business business = businessRepository.findById(businessNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        User user = userRepository.findById(business.getUserNo().getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        Sale sale = saleRepository.findById(business.getSaleNo().getNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        return new BusinessResponseDTO(business, user, sale);
    }
    
    // 사용자의 모든 거래 내역 조회
    public List<BusinessListResponseDTO> findAllList(Long userNo) {
        List<Business> businessLists = businessRepository.findByUserNo_Id(userNo);
        List<BusinessListResponseDTO> responseDTO = businessLists.stream()
                .map(business -> new BusinessListResponseDTO(
                        business.getBusinessNo(),
                        business.getTotalPrice(),
                        business.getStatus(),
                        business.getUserNo().getId(),
                        business.getUserNo().getName(),
                        business.getSaleNo().getNo(),
                        business.getSaleNo().getSaleName(),
                        business.getSaleNo().getSaleLocation(),
                        business.getSaleNo().getArea(),
                        business.getSaleNo().getCategory(),
                        business.getSaleNo().getSalePrice(),
                        business.getSaleNo().getDepositPrice(),
                        business.getSaleNo().getRentPrice(),
                        business.getSaleNo().getDescription(),
                        business.getSaleNo().getSaleStatus()
                ))
                .collect(Collectors.toList());
        return responseDTO;
    }
    
    // 거래 내역 등록
    public BusinessResponseDTO registerBusiness(@PathVariable long business_no, BusinessRegisterRequestDTO businessRegisterRequestDTO) {
        Business business = new Business();
        User user = userRepository.findById(businessRegisterRequestDTO.getUserNo())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_BUSINESS));
        Sale sale = saleRepository.findById(businessRegisterRequestDTO.getSaleNo())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SALE));

        business.setUserNo(user);
        business.setSaleNo(sale);
        business.setTotalPrice(businessRegisterRequestDTO.getTotalPrice());
        business.setStatus(businessRegisterRequestDTO.getStatus());

        Business savedBusiness = businessRepository.save(business);

        return new BusinessResponseDTO(savedBusiness, user, sale);
    }

    // 거래 내역 수정
    public Optional<Void> updateBusiness(@PathVariable Long businessNo, @RequestBody BusinessUpdateRequestDTO businessUpdateRequestDTO) {
        Business business = businessRepository.findById(businessNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        business.update(business, businessUpdateRequestDTO);

        return Optional.empty();
    }

    // 거래 내역 취소
    public Optional<Void> cancelBusiness(@PathVariable Long businessNo, BusinessCancelRequestDTO businessCancelRequestDTO) {
        Business business = businessRepository.findById(businessNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        cancel(business, businessCancelRequestDTO);

        return Optional.empty();
    }

    private void cancel(Business business, BusinessCancelRequestDTO businessCancelRequestDTO) {
        business.setStatus((businessCancelRequestDTO.getStatus()));
    }

    // 거래 내역 삭제
    public Optional<Void> deleteBusiness(@PathVariable Long businessNo) {
        Business business = businessRepository.findById(businessNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        businessRepository.delete(business);

        return Optional.empty();
    }
}
