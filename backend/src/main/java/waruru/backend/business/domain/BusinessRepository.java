package waruru.backend.business.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
//    Optional<Business> cancelBusiness(Long businessNo);
}

