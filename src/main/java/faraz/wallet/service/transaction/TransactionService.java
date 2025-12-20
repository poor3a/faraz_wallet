package faraz.wallet.service.transaction;

import faraz.wallet.dto.response.TransactionSummaryResponse;
import faraz.wallet.dto.response.TransactionResponse;
import faraz.wallet.entity.Transaction;
import faraz.wallet.entity.Wallet;
import faraz.wallet.entity.TransactionType;
import faraz.wallet.repository.TransactionRepository;
import faraz.wallet.service.SystemLogService;
import faraz.wallet.service.wallet.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    private final SystemLogService systemLogService;

    public TransactionService(
            TransactionRepository transactionRepository,
            WalletService walletService,
            SystemLogService systemLogService
    ) {
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
        this.systemLogService = systemLogService;
    }

    public List<TransactionResponse> getMyTransactions(Long userId) {

        Wallet wallet = walletService.getOrCreateWallet(userId);

        List<Transaction> transactions =
                transactionRepository.findAllByWallet(wallet);

        systemLogService.log(
                "TRANSACTION_LIST_VIEW",
                wallet.getUser().getPhoneNumber(),
                "User viewed transaction list"
        );

        return transactions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TransactionSummaryResponse getMyTransactionSummary(Long userId) {

        Wallet wallet = walletService.getOrCreateWallet(userId);

        List<Transaction> transactions =
                transactionRepository.findAllByWallet(wallet);

        BigDecimal totalCredit = BigDecimal.ZERO;
        BigDecimal totalDebit = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.CREDIT) {
                totalCredit = totalCredit.add(transaction.getAmount());
            } else if (transaction.getType() == TransactionType.DEBIT) {
                totalDebit = totalDebit.add(transaction.getAmount());
            }
        }

        systemLogService.log(
                "TRANSACTION_SUMMARY_VIEW",
                wallet.getUser().getPhoneNumber(),
                "User viewed transaction summary"
        );

        return new TransactionSummaryResponse(
                totalCredit,
                totalDebit,
                transactions.size()
        );
    }

    private TransactionResponse toResponse(Transaction t) {
        return new TransactionResponse(
                t.getAmount(),
                t.getType().name(),
                t.getStatus(),
                t.getDescription(),
                t.getCreatedAt()
        );
    }
}
