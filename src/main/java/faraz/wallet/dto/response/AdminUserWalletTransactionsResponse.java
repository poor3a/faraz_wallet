package faraz.wallet.dto.response;

import faraz.wallet.dto.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public record AdminUserWalletTransactionsResponse(
        String phoneNumber,
        String accountId,
        BigDecimal balance,
        List<TransactionResponse> transactions
) {}
