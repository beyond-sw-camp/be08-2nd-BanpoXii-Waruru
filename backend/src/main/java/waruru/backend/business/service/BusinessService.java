package waruru.backend.business.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import waruru.backend.business.domain.Business;
import waruru.backend.business.domain.BusinessRepository;
import waruru.backend.business.dto.*;
import waruru.backend.common.exception.ErrorCode;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final MemberRepository memberRepository;
    private final SaleRepository saleRepository;

    public BusinessService(BusinessRepository businessRepository, MemberRepository memberRepository, SaleRepository saleRepository) {

        this.businessRepository = businessRepository;
        this.memberRepository = memberRepository;
        this.saleRepository = saleRepository;
    }

    @Transactional
    public BusinessResponseDTO findBusinessByBusinessNo(Long businessNo) {

        Business business = businessRepository.findById(businessNo)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_BUSINESS));

        Member member = memberRepository.findById(business.getUserNo().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        Sale sale = saleRepository.findById(business.getSaleNo().getSaleNo())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SALE));

        BusinessResponseDTO responseDTO = BusinessResponseDTO.builder()
                .businessNo(business.getBusinessNo())
                .totalPrice(business.getTotalPrice())
                .status(business.getStatus())
                .userNo(member.getId())
                .name(member.getName())
                .saleNo(sale.getSaleNo())
                .saleName(sale.getSaleName())
                .saleLocation(sale.getSaleLocation())
                .area(sale.getArea())
                .category(sale.getCategory())
                .salePrice(sale.getSalePrice())
                .depositPrice(sale.getDepositPrice())
                .rentPrice(sale.getRentPrice())
                .description(sale.getDescription())
                .saleStatus(sale.getSaleStatus())
                .createdDate(business.getCreatedDate() != null ? business.getCreatedDate() : null)
                .updatedDate(business.getUpdatedDate() != null ? business.getUpdatedDate() : null)
                .build();

        return responseDTO;
    }

    @Transactional
    public List<BusinessListResponseDTO> findAllList(Long userNo) {

        List<Business> businessLists = businessRepository.findByUserNo_Id(userNo);

        if (businessLists.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_BUSINESS);
        }
        List<BusinessListResponseDTO> responseDTO = businessLists.stream()
                .map(business -> new BusinessListResponseDTO(
                        business.getBusinessNo(),
                        business.getTotalPrice(),
                        business.getStatus(),
                        business.getUserNo().getId(),
                        business.getUserNo().getName(),
                        business.getSaleNo().getSaleNo(),
                        business.getSaleNo().getSaleName(),
                        business.getSaleNo().getSaleLocation(),
                        business.getSaleNo().getArea(),
                        business.getSaleNo().getCategory(),
                        business.getSaleNo().getSalePrice(),
                        business.getSaleNo().getDepositPrice(),
                        business.getSaleNo().getRentPrice(),
                        business.getSaleNo().getDescription(),
                        business.getSaleNo().getSaleStatus(),
                        business.getSaleNo().getCreatedDate(),
                        business.getSaleNo().getUpdatedDate()
                ))
                .collect(Collectors.toList());
        return responseDTO;
    }

    @Transactional
    public BusinessResponseDTO registerBusiness(BusinessRegisterRequestDTO businessRegisterRequestDTO) {

        Business business = new Business();

        Member member = memberRepository.findById(businessRegisterRequestDTO.getUserNo())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        Sale sale = saleRepository.findById(businessRegisterRequestDTO.getSaleNo())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SALE));

        business.setUserNo(member);
        business.setSaleNo(sale);
        business.setTotalPrice(businessRegisterRequestDTO.getTotalPrice());
        business.setStatus(businessRegisterRequestDTO.getStatus());
        business.setCreatedDate(businessRegisterRequestDTO.getCreatedDate());

        business = businessRepository.save(business);

        return BusinessResponseDTO.of(business);
    }

    @Transactional
    public void updateBusiness(Long businessNo, BusinessUpdateRequestDTO businessUpdateRequestDTO) {

        Business business = businessRepository.findById(businessNo)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_BUSINESS));

        business.update(businessUpdateRequestDTO);

        businessRepository.save(business);
    }

    @Transactional
    public Optional<Void> cancelBusiness(@PathVariable Long businessNo, BusinessCancelRequestDTO businessCancelRequestDTO) {

        Business business = businessRepository.findById(businessNo)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_BUSINESS));

        cancel(business, businessCancelRequestDTO);

        return Optional.empty();
    }

    @Transactional
    public void cancel(Business business, BusinessCancelRequestDTO businessCancelRequestDTO) {

        business.setStatus(businessCancelRequestDTO.getStatus());
    }

    @Transactional
    public void deleteBusiness(@PathVariable long businessNo) {

        Business business = businessRepository.findById(businessNo)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_BUSINESS));

        businessRepository.delete(business);
    }
}
