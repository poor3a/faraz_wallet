package faraz.wallet.controller.superAdmin;

import faraz.wallet.dto.request.SystemLogQueryRequest;
import faraz.wallet.dto.response.SystemLogResponse;
import faraz.wallet.service.adminstator.SuperAdminLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/super_admin/logs")
public class SuperAdminLogController {

    private final SuperAdminLogService logService;

    @PostMapping
    public ResponseEntity<List<SystemLogResponse>> getLogsBetween(
            @Valid @RequestBody SystemLogQueryRequest request
    ) {
        return ResponseEntity.ok(
                logService.getLogsBetween(
                        request.getFrom(),
                        request.getTo()
                )
        );
    }
}
