package waruru.backend.sale.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import waruru.backend.sale.dto.*;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
//    List<Sale> findAllList();
}
