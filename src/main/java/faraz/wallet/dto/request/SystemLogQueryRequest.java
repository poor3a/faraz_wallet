package faraz.wallet.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SystemLogQueryRequest {

    @NotNull(message = "from is required")
    private Instant from;

    @NotNull(message = "to is required")
    private Instant to;

}
