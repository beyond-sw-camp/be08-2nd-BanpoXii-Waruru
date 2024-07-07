package waruru.backend.detail.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waruru.backend.detail.domain.Detail;
import waruru.backend.detail.domain.DetailRepository;
import waruru.backend.detail.dto.DetailRegisterRequestDTO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class DetailService {

    private final DetailRepository detailRepository;

    @Transactional
    public Optional<Detail> registerDetail(DetailRegisterRequestDTO detailRegisterRequestDTO) {

//        if(detailRepository.existsByName(detailRegisterRequestDTO.getTitle())) {
////            throw new
////        }

        Detail detail = Detail.builder()
                .saleNo(detailRegisterRequestDTO.getSaleNo())
                .userNo(detailRegisterRequestDTO.getUserNo())
                .title(detailRegisterRequestDTO.getTitle())
                .category(detailRegisterRequestDTO.getCategory())
                .description(detailRegisterRequestDTO.getDescription())
                .price(detailRegisterRequestDTO.getPrice())
                .detailDate(detailRegisterRequestDTO.getDetailDate())
                .build();

        return Optional.of(detailRepository.save(detail));
    }

    public List<Detail> getAllDetails() {   return detailRepository.findAll();  }


}
