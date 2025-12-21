package faraz.wallet.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class SystemLogResponse {

    private final String action;
    private final String username;
    private final String description;
    private final Instant createdAt;

}
