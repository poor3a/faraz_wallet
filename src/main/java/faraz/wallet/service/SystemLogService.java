package faraz.wallet.service;

import faraz.wallet.entity.SystemLog;
import faraz.wallet.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SystemLogService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SystemLogService.class);

    private final SystemLogRepository systemLogRepository;

    public void log(String action, String username, String description) {

        LOGGER.info("SystemLog action={} username={}", action, username);

        SystemLog log = new SystemLog();
        log.setAction(action);
        log.setUsername(username);
        log.setDescription(description);
        log.setCreatedAt(Instant.now());

        systemLogRepository.save(log);
    }
}
