package waruru.backend.sale.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.sale.dto.*;

import java.util.List;
import java.util.stream.Collectors;

import static waruru.backend.common.exception.ErrorCode.NOT_FOUND_SALE;
import static waruru.backend.common.exception.ErrorCode.NOT_FOUND_USER;

@Service
@Transactional
public class SaleService {
    private final SaleRepository saleRepository;
    private final MemberRepository memberRepository;

    public SaleService(SaleRepository saleRepository, MemberRepository memberRepository) {
        this.saleRepository = saleRepository;
        this.memberRepository = memberRepository;
    }
    public SaleResponseDTO registerSale(SaleRegisterRequestDTO registerRequestDTO) {
        Sale sale = new Sale();
        Member member = memberRepository.findById(registerRequestDTO.getUserNo()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_USER.getMessage() + " no = " + registerRequestDTO.getUserNo()));

        sale.setUserNo(member);
        sale.setSaleName(registerRequestDTO.getSaleName());
        sale.setSaleLocation(registerRequestDTO.getSaleLocation());
        sale.setArea(registerRequestDTO.getArea());
        sale.setCategory(registerRequestDTO.getCategory());
        sale.setSalePrice(registerRequestDTO.getSalePrice());
        sale.setDepositPrice(registerRequestDTO.getDepositPrice());
        sale.setRentPrice(registerRequestDTO.getRentPrice());
        sale.setDescription(registerRequestDTO.getDescription());
        sale.setSaleStatus(registerRequestDTO.getSaleStatus());
        Sale savedSale = saleRepository.save(sale);
        return new SaleResponseDTO(savedSale);
    }

    public SaleResponseDTO findById(Long no) {
        //상세 정보 확인
        Sale sale = saleRepository.findById(no).orElseThrow(()
                -> new IllegalArgumentException(NOT_FOUND_SALE.getMessage() + " no = " + no));
        sale.setReviewCount(saleRepository.getReviewCount(sale.getNo()));
        return new SaleResponseDTO(sale);
    }

    public void deleteSale(Long no) {
        Sale sale = saleRepository.findById(no).orElseThrow(()
                -> new IllegalArgumentException(NOT_FOUND_SALE.getMessage() + " no = " + no));
        saleRepository.delete(sale);
    }

    public List<SaleListResponseDTO> findAllList() {
        List<Sale> sales = saleRepository.findAll();
        List<SaleListResponseDTO> responseDTO = sales.stream()
                .map(sale -> new SaleListResponseDTO(
                        sale.getUserNo().getNickname(),
                        //sale.getUserNo().getId(),
                        sale.getSaleName(),
                        sale.getSaleLocation(),
                        sale.getArea(),
                        sale.getCategory(),
                        sale.getSalePrice(),
                        sale.getDepositPrice(),
                        sale.getRentPrice(),
                        //sale.getRegisterDate()
                        saleRepository.getReviewCount(sale.getNo()),
                        sale.getCreatedDate()
                ))
                .collect(Collectors.toList());
        return responseDTO;
    }

    public void updateSale(Long no, SaleUpdateRequestDTO updateRequestDTO) {
        Sale sale = saleRepository.findById(no).orElseThrow(()
                -> new IllegalArgumentException(NOT_FOUND_SALE.getMessage() + " no = " + no));
        mapToUpdate(sale, updateRequestDTO);
        saleRepository.save(sale);
    }

    private void mapToUpdate(Sale sale, SaleUpdateRequestDTO updateDTO) {
        sale.setSaleName(updateDTO.getSaleName() != null ? updateDTO.getSaleName() : sale.getSaleName());
        sale.setSaleLocation(updateDTO.getSaleLocation() != null ? updateDTO.getSaleLocation() : sale.getSaleLocation());
        sale.setArea(updateDTO.getArea() != null ? updateDTO.getArea() : sale.getArea()); //
        sale.setCategory(updateDTO.getCategory() != null ? updateDTO.getCategory() : sale.getCategory());
        sale.setSalePrice(updateDTO.getSalePrice() != null ? updateDTO.getSalePrice() : sale.getSalePrice()); //
        sale.setDepositPrice(updateDTO.getDepositPrice() != null ? updateDTO.getDepositPrice() : sale.getDepositPrice()); //
        sale.setRentPrice(updateDTO.getRentPrice() != null ? updateDTO.getRentPrice() : sale.getRentPrice()); //
        sale.setDescription(updateDTO.getDescription() != null ? updateDTO.getDescription() : sale.getDescription());
        sale.setSaleStatus(updateDTO.getSaleStatus() != null ? updateDTO.getSaleStatus() : sale.getSaleStatus());
    }

}
