package waruru.backend.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 사용자 & 매물에 관한 후기 검증
    boolean existsByUserNo_IdAndSaleNo_No(Long userNo, Long saleNo);

}