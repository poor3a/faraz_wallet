package faraz.wallet.service.adminstator;

import faraz.wallet.dto.response.SystemLogResponse;
import faraz.wallet.entity.SystemLog;
import faraz.wallet.exception.ApiException;
import faraz.wallet.repository.SystemLogRepository;
import faraz.wallet.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperAdminLogService {

    private final SystemLogRepository systemLogRepository;
    private final SystemLogService systemLogService;

    public List<SystemLogResponse> getLogsBetween(Instant from, Instant to) {

        if (from.isAfter(to)) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "'from' must be before 'to'"
            );
        }

        systemLogService.log(
                "SUPER_ADMIN_LOG_QUERY",
                "SYSTEM",
                "Super admin queried logs from " + from + " to " + to
        );

        return systemLogRepository.findAllByCreatedAtBetween(from, to)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SystemLogResponse toResponse(SystemLog log) {
        return new SystemLogResponse(
                log.getAction(),
                log.getUsername(),
                log.getDescription(),
                log.getCreatedAt()
        );
    }
}
