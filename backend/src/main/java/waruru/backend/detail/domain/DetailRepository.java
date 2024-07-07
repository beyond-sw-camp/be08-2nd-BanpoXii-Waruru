package waruru.backend.detail.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DetailRepository extends JpaRepository<Detail, Long> {
    boolean existsByTitle(String Title);
    Optional<Detail> findByTitle(String Title);
}
