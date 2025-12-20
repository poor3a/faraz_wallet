package faraz.wallet.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionRequest {

    @NotNull
    private BigDecimal amount;

    private String description;

}
