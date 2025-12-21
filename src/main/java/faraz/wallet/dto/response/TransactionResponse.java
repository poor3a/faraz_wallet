package faraz.wallet.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import faraz.wallet.enums.TransactionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransactionResponse {

    private final BigDecimal amount;
    private final String type;
    private final TransactionStatus status;
    private final String description;
    private final Instant createdAt;



}
