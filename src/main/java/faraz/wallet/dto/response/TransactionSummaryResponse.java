package faraz.wallet.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class TransactionSummaryResponse {

    private final BigDecimal totalCredit;
    private final BigDecimal totalDebit;
    private final int transactionCount;



}
