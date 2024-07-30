package waruru.backend.detail.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import waruru.backend.common.exception.ErrorCode;
import waruru.backend.common.exception.NotFoundException;
import waruru.backend.detail.domain.Detail;
import waruru.backend.detail.domain.DetailRepository;
import waruru.backend.detail.dto.DetailRegisterRequestDTO;
import waruru.backend.detail.dto.DetailResponseDTO;
import waruru.backend.detail.dto.DetailUpdateRequestDTO;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.sale.domain.Sale;
import waruru.backend.sale.domain.SaleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Transactional
public class DetailService {

    private final DetailRepository detailRepository;
    private final MemberRepository memberRepository;
    private final SaleRepository saleRepository;

    public Optional<Detail> registerDetail(DetailRegisterRequestDTO detailRegisterRequestDTO) {

        Sale sale = saleRepository.findById(detailRegisterRequestDTO.getSaleNo()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_SALE)
        );

        Member member = memberRepository.findById(detailRegisterRequestDTO.getUserNo()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_USER)
        );

        Detail detail = Detail.builder()
                .saleNo(sale)
                .userNo(member)
                .title(detailRegisterRequestDTO.getTitle())
                .category(detailRegisterRequestDTO.getCategory())
                .description(detailRegisterRequestDTO.getDescription())
                .price(detailRegisterRequestDTO.getPrice())
                .detailDate(detailRegisterRequestDTO.getDetailDate())
                .build();

        return Optional.of(detailRepository.save(detail));
    }

    public List<DetailResponseDTO> getAllDetails(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Detail> detailPage = detailRepository.findAll(pageable);

        return detailPage.getContent().stream()
                .map(DetailResponseDTO::of)
                .collect(Collectors.toList());
    }

    public DetailResponseDTO getDetailById(Long detailNo) {

        Detail detail = detailRepository.findById(detailNo).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_DETAIL)
        );

        return DetailResponseDTO.of(detail);
    }

    public Optional<Void> updateDetail(Long detailNo, DetailUpdateRequestDTO detailUpdateRequestDTO) {

        Detail detail = detailRepository.findById(detailNo).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_DETAIL)
        );

        detail.update(detailUpdateRequestDTO);

        detailRepository.save(detail);

        return Optional.empty();
    }

    public void deleteDetail(Long detailNo) {

        Detail detail = detailRepository.findById(detailNo).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_DETAIL));

        detailRepository.delete(detail);
    }
}
