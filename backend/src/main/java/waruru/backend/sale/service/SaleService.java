package waruru.backend.sale.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.sale.dto.*;
import waruru.backend.user.domain.User;
import waruru.backend.user.domain.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    public SaleService(SaleRepository saleRepository, UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
    }
    public SaleResponseDTO registerSale(SaleRegisterRequestDTO registerRequestDTO) {
        //PK 추가
        Sale sale = new Sale();
        User user = userRepository.findById(registerRequestDTO.getUserNo())
                .orElseThrow(() -> new EntityNotFoundException("회원 ID 조회 불가 : " + registerRequestDTO.getUserNo()));

        sale.setUserNo(user);
        sale.setSaleName(registerRequestDTO.getSaleName());
        sale.setSaleLocation(registerRequestDTO.getSaleLocation());
        sale.setArea(registerRequestDTO.getArea());
        sale.setCategory(registerRequestDTO.getCategory());
        sale.setSalePrice(registerRequestDTO.getSalePrice());
        sale.setDepositPrice(registerRequestDTO.getDepositPrice());
        sale.setRentPrice(registerRequestDTO.getRentPrice());
        sale.setDescription(registerRequestDTO.getDescription());
        sale.setSaleStatus(registerRequestDTO.getSaleStatus());
        sale.setRegisterDate(registerRequestDTO.getRegisterDate() != null ? registerRequestDTO.getRegisterDate() : LocalDateTime.now());
        sale.setUpdateDate(registerRequestDTO.getUpdateDate() != null ? registerRequestDTO.getUpdateDate() : LocalDateTime.now());

        Sale savedSale = saleRepository.save(sale);
        return new SaleResponseDTO(savedSale);
    }

    public SaleResponseDTO findById(Long no) {
        //상세 정보 확인
        Sale sale = saleRepository.findById(no).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. no = " + no));
        return new SaleResponseDTO(sale);
    }

    public void deleteSale(Long no) {
        Sale sale = saleRepository.findById(no).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. no = " + no));
        saleRepository.delete(sale);
    }

    public List<SaleListResponseDTO> findAllList() {
        List<Sale> sales = saleRepository.findAll();
        List<SaleListResponseDTO> responseDTO = sales.stream()
                .map(sale -> new SaleListResponseDTO(
                        //sale.getUserNo().getNickname(),
                        sale.getUserNo().getId(),
                        sale.getSaleName(),
                        sale.getSaleLocation(),
                        sale.getArea(),
                        sale.getCategory(),
                        sale.getSalePrice(),
                        sale.getDepositPrice(),
                        sale.getRentPrice(),
                        sale.getRegisterDate()
                ))
                .collect(Collectors.toList());
        return responseDTO;
    }

    public void updateSale(Long no, SaleUpdateRequestDTO updateRequestDTO) {
        Sale sale = saleRepository.findById(no).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. no = " + no));
        mapToUpdate(sale, updateRequestDTO);
        saleRepository.save(sale);
    }

//    private Sale mapToEntity(SaleRegisterRequestDTO dto) {
//        Sale sale = Sale.builder()
//                .saleName(dto.getSaleName())
//                .saleLocation(dto.getSaleLocation())
//                .area(dto.getArea())
//                .category(dto.getCategory())
//                .salePrice(dto.getSalePrice())
//                .depositPrice(dto.getDepositPrice())
//                .rentPrice(dto.getRentPrice())
//                .description(dto.getDescription())
//                .saleStatus(dto.getSaleStatus())
//                .registerDate(dto.getRegisterDate())
//                .updateDate(dto.getUpdateDate())
//                .build();
//
//        return sale;
//    }

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