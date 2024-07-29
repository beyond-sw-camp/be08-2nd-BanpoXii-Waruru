package waruru.backend.member.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends CrudRepository<Email, String> {

    Optional<Email> findByEmail(String email);

    void deleteByEmail(String email);
}
