package faraz.wallet.service.adminstator;

import faraz.wallet.entity.SystemLog;
import faraz.wallet.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperAdminLogService {

    private final SystemLogRepository systemLogRepository;

    public List<SystemLog> getLogsBetween(Instant from, Instant to) {
        return systemLogRepository.findAllByCreatedAtBetween(from, to);
    }
}
