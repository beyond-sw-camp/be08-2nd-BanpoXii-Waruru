package waruru.backend.sale.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.sale.dto.SaleListResponseDTO;
import waruru.backend.sale.dto.SaleRegisterRequestDTO;
import waruru.backend.sale.dto.SaleResponseDTO;
import waruru.backend.sale.dto.SaleUpdateRequestDTO;

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

    public List<SaleListResponseDTO> findAllList() {

        List<Sale> sales = saleRepository.findAll();

        List<SaleListResponseDTO> responseDTO = sales.stream()
                .map(sale -> new SaleListResponseDTO(
                        sale.getUserNo().getNickname(),
                        sale.getSaleName(),
                        sale.getSaleLocation(),
                        sale.getArea(),
                        sale.getCategory(),
                        sale.getSalePrice(),
                        sale.getDepositPrice(),
                        sale.getRentPrice(),
                        saleRepository.getReviewCount(sale.getSaleNo()),
                        sale.getCreatedDate()
                ))
                .collect(Collectors.toList());

        return responseDTO;
    }

    public SaleResponseDTO findById(Long saleNo) {

        Sale sale = saleRepository.findById(saleNo).orElseThrow(()
                -> new IllegalArgumentException(NOT_FOUND_SALE.getMessage() + " saleNo = " + saleNo));

        sale.setReviewCount(saleRepository.getReviewCount(sale.getSaleNo()));

        return new SaleResponseDTO(sale);
    }

    public void updateSale(Long saleNo, SaleUpdateRequestDTO updateRequestDTO) {

        Sale sale = saleRepository.findById(saleNo).orElseThrow(()
                -> new IllegalArgumentException(NOT_FOUND_SALE.getMessage() + " no = " + saleNo));

        mapToUpdate(sale, updateRequestDTO);

        saleRepository.save(sale);
    }

    public void deleteSale(Long saleNo) {

        Sale sale = saleRepository.findById(saleNo).orElseThrow(()
                -> new IllegalArgumentException(NOT_FOUND_SALE.getMessage() + " saleNo = " + saleNo));

        saleRepository.delete(sale);
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
