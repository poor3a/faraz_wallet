package faraz.wallet.dto.response;

import java.math.BigDecimal;

public class TransactionSummaryResponse {

    private final BigDecimal totalCredit;
    private final BigDecimal totalDebit;
    private final int transactionCount;

    public TransactionSummaryResponse(
            BigDecimal totalCredit,
            BigDecimal totalDebit,
            int transactionCount
    ) {
        this.totalCredit = totalCredit;
        this.totalDebit = totalDebit;
        this.transactionCount = transactionCount;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public int getTransactionCount() {
        return transactionCount;
    }
}
