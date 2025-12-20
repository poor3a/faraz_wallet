package faraz.wallet.service;

import faraz.wallet.entity.SystemLog;
import faraz.wallet.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SystemLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogService.class);

    private final SystemLogRepository systemLogRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(String action, String username, String description) {
        try {
            LOGGER.info(action+"  [[" + description +"  done by " + username  + "]]" );
            SystemLog log = new SystemLog();
            log.setAction(action);
            log.setUsername(username);
            log.setDescription(description);
            log.setCreatedAt(Instant.now());
            systemLogRepository.save(log);
        } catch (Exception ignored) {
        }
    }
}
