package faraz.wallet.controller.superAdmin;

import faraz.wallet.entity.SystemLog;
import faraz.wallet.service.adminstator.SuperAdminLogService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/super_admin/logs")
public class SuperAdminLogController {

    private final SuperAdminLogService logService;

    public SuperAdminLogController(SuperAdminLogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public List<SystemLog> getLogsBetween(
            @RequestParam Instant from,
            @RequestParam Instant to
    ) {
        return logService.getLogsBetween(from, to);
    }
}
