package faraz.wallet.service.adminstator;

import faraz.wallet.entity.SystemLog;
import faraz.wallet.repository.SystemLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SuperAdminLogService {

    private final SystemLogRepository systemLogRepository;

    public SuperAdminLogService(SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    public List<SystemLog> getLogsBetween(Instant from, Instant to) {
        return systemLogRepository.findAllByCreatedAtBetween(from, to);
    }
}
