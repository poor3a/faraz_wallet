package faraz.wallet.repository;

import faraz.wallet.entity.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {

    List<SystemLog> findAllByUsername(String username);

    List<SystemLog> findAllByCreatedAtBetween(
            Instant from,
            Instant to
    );
}
