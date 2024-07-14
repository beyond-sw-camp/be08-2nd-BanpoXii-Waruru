package waruru.backend.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessRepository;
import waruru.backend.business.domain.BusinessStatus;
import waruru.backend.business.dto.*;
import waruru.backend.common.exception.BusinessException;
import waruru.backend.common.exception.ErrorCode;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.sale.domain.SaleStatus;
import waruru.backend.user.domain.User;
import waruru.backend.user.domain.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

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
    public BusinessResponseDTO findBusinessByBusinessNo(@PathVariable Long businessNo, BusinessRequestDTO businessRequestDTO) {
        Business business = businessRepository.findById(businessNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        User user = userRepository.findById(business.getUserNo().getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        Sale sale = saleRepository.findById(business.getSaleNo().getNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        return new BusinessResponseDTO(
                business.getBusinessNo(),
                business.getTotalPrice(),
                business.getStatus(),
                user.getId(),
                user.getName(),
                sale.getNo(),
                sale.getSaleName(),
                sale.getSaleLocation(),
                sale.getArea(),
                sale.getCategory(),
                sale.getSalePrice(),
                sale.getDepositPrice(),
                sale.getRentPrice(),
                sale.getDescription(),
                sale.getSaleStatus()
        );
    }

    // 거래 내역 등록
    public BusinessResponseDTO registerBusiness(@PathVariable Long userNo, BusinessRegisterRequestDTO businessRegisterRequestDTO) {

        Business business = businessRepository.findById(userNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        Sale sale = saleRepository.findById(userNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_SALE));


        business = businessRepository.save(business);

        return new BusinessResponseDTO(
                business.getBusinessNo(),
                business.getTotalPrice(),
                business.getStatus(),
                user.getId(),
                user.getName(),
                sale.getNo(),
                sale.getSaleName(),
                sale.getSaleLocation(),
                sale.getArea(),
                sale.getCategory(),
                sale.getSalePrice(),
                sale.getDepositPrice(),
                sale.getRentPrice(),
                sale.getDescription(),
                sale.getSaleStatus()
        );
    }


    // 거래 내역 수정
    public BusinessResponseDTO updateBusiness(@PathVariable Long businessNo, BusinessUpdateRequestDTO businessUpdateRequestDTO) {
        Business business = businessRepository.findById(businessNo)
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        User user = userRepository.findById(businessNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        Sale sale = saleRepository.findById(businessNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS));

        update(business, businessUpdateRequestDTO);

        return new BusinessResponseDTO(
                business.getBusinessNo(),
                business.getTotalPrice(),
                business.getStatus(),
                user.getId(),
                user.getName(),
                sale.getNo(),
                sale.getSaleName(),
                sale.getSaleLocation(),
                sale.getArea(),
                sale.getCategory(),
                sale.getSalePrice(),
                sale.getDepositPrice(),
                sale.getRentPrice(),
                sale.getDescription(),
                sale.getSaleStatus()
        );
    }

    private void update(Business business, BusinessUpdateRequestDTO businessUpdateRequestDTO) {
        business.setTotalPrice((business.getTotalPrice() != businessUpdateRequestDTO.getTotalPrice()) ? business.getTotalPrice() : businessUpdateRequestDTO.getTotalPrice());
        business.setStatus((business.getStatus() != businessUpdateRequestDTO.getStatus()) ? business.getStatus() : businessUpdateRequestDTO.getStatus());
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
