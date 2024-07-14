package waruru.backend.detail.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waruru.backend.common.exception.ErrorCode;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.detail.domain.Detail;
import waruru.backend.detail.domain.DetailRepository;
import waruru.backend.detail.dto.DetailDeleteRequestDTO;
import waruru.backend.detail.dto.DetailRegisterRequestDTO;
import waruru.backend.detail.dto.DetailResponseDTO;
import waruru.backend.detail.dto.DetailUpdateRequestDTO;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;
import waruru.backend.user.domain.User;
import waruru.backend.user.domain.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class DetailService {

    private final DetailRepository detailRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;

    @Transactional
    public Optional<Detail> registerDetail(DetailRegisterRequestDTO detailRegisterRequestDTO) {

        Sale sale = saleRepository.findById(detailRegisterRequestDTO.getSaleNo()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_SALE)
        );

        User user = userRepository.findById(detailRegisterRequestDTO.getUserNo()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_USER)
        );

        Detail detail = Detail.builder()
                .saleNo(sale)
                .userNo(user)
                .title(detailRegisterRequestDTO.getTitle())
                .category(detailRegisterRequestDTO.getCategory())
                .description(detailRegisterRequestDTO.getDescription())
                .price(detailRegisterRequestDTO.getPrice())
                .detailDate(detailRegisterRequestDTO.getDetailDate())
                .build();

        return Optional.of(detailRepository.save(detail));
    }

    @Transactional
    public List<DetailResponseDTO> getAllDetails() {
        return Collections.unmodifiableList(DetailResponseDTO.listOf(detailRepository.findAll()));
    }

    @Transactional
    public DetailResponseDTO getDetailById(Long detailNo) {
        Detail detail = detailRepository.findById(detailNo)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_DETAIL));

        return DetailResponseDTO.of(detail);
    }

    public Optional<Void> updateDetail(DetailUpdateRequestDTO detailUpdateRequestDTO) {
        Detail detail = detailRepository.findByTitle(detailUpdateRequestDTO.getTitle()).orElseThrow(() ->
                new IllegalArgumentException("Invalid Detail id:" + detailUpdateRequestDTO.getTitle()));

        detail.update(detailUpdateRequestDTO);

        return Optional.empty();
    }

    public void deleteDetail(DetailDeleteRequestDTO detailDeleteRequestDTO) {
        Detail detail = detailRepository.findByTitle(detailDeleteRequestDTO.getDetailTitle()).orElseThrow(() ->
                new IllegalArgumentException("Invalid Detail id:" + detailDeleteRequestDTO.getDetailTitle()));

        detailRepository.delete(detail);
    }
}
