package faraz.wallet.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import faraz.wallet.entity.TransactionStatus;

public class TransactionResponse {

    private final BigDecimal amount;
    private final String type;
    private final TransactionStatus status;
    private final String description;
    private final Instant createdAt;

    public TransactionResponse(
            BigDecimal amount,
            String type,
            TransactionStatus status,
            String description,
            Instant createdAt
    ) {
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
