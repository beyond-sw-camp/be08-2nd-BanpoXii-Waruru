package waruru.backend.sale.service;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.sale.dto.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleService {
    private final SaleRepository saleRepository;
    private long sequence = 0L;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Optional<Sale> save(SaleRegisterRequestDTO registerRequestDTO) {
        //PK 추가
        Sale sale = mapToEntity(registerRequestDTO);
        sale.setNo(++sequence);
        return Optional.of(saleRepository.save(sale));
    }

    public SaleResponseDTO findById(Long no) {
        //상세 정보 확인
        Sale sale = saleRepository.findById(no).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. no = " + no));
        return SaleResponseDTO.from(sale);
    }

    public List<SaleListResponseDTO> findAllList() {
        //리스트 조회할 칼럼만 특정해서 조회
        List<Sale> sale = saleRepository.findAllList();
        return sale.stream()
                .map(p -> new SaleListResponseDTO(p.getNo(),p.getUserNo(),p.getSaleName(),p.getSaleLocation(),
                        p.getArea(),p.getCategory(),p.getSalePrice(),p.getDepositPrice(),p.getRentPrice(),
                        p.getRegisterDate()))
                .collect(Collectors.toList());
    }

    public void delete(SaleDeleteRequestDTO saleDeleteRequestDTO) {
        // 무슨 필드를 기준으로 잡고 지워야 하지..?
        Sale sale = saleRepository.findById(saleDeleteRequestDTO.getNo()).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. no = " + saleDeleteRequestDTO.getNo()));
        saleRepository.delete(sale);
    }


    public void update(Long no, SaleUpdateRequestDTO updateRequestDTO) {
        Sale sale = saleRepository.findById(no).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. no = " + no));
        mapToUpdate(sale, updateRequestDTO);
        saleRepository.save(sale);
    }

    private Sale mapToEntity(SaleRegisterRequestDTO dto) {
        return Sale.builder()
                .userNo(dto.getUserNo())
                .saleName(dto.getSaleName())
                .saleLocation(dto.getSaleLocation())
                .area(dto.getArea())
                .category(dto.getCategory())
                .salePrice(dto.getSalePrice())
                .depositPrice(dto.getDepositPrice())
                .rentPrice(dto.getRentPrice())
                .description(dto.getDescription())
                .saleStatus(dto.getSaleStatus())
                .registerDate(dto.getRegisterDate())
                .updateDate(dto.getUpdateDate())
                .build();
    }

    private void mapToUpdate(Sale sale, SaleUpdateRequestDTO updateDTO) {
        sale.setSaleName(updateDTO.getSaleName() != null ? updateDTO.getSaleName() : sale.getSaleName());
        sale.setSaleLocation(updateDTO.getSaleLocation() != null ? updateDTO.getSaleLocation() : sale.getSaleLocation());
        sale.setArea(updateDTO.getArea() != 0 ? updateDTO.getArea() : sale.getArea());
        sale.setCategory(updateDTO.getCategory() != null ? updateDTO.getCategory() : sale.getCategory());
        sale.setSalePrice(updateDTO.getSalePrice() != 0 ? updateDTO.getSalePrice() : sale.getSalePrice());
        sale.setDepositPrice(updateDTO.getDepositPrice() != 0 ? updateDTO.getDepositPrice() : sale.getDepositPrice());
        sale.setRentPrice(updateDTO.getRentPrice() != 0 ? updateDTO.getRentPrice() : sale.getRentPrice());
        sale.setDescription(updateDTO.getDescription() != null ? updateDTO.getDescription() : sale.getDescription());
        sale.setSaleStatus(updateDTO.getSaleStatus() != null ? updateDTO.getSaleStatus() : sale.getSaleStatus());
    }

}