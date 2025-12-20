package faraz.wallet.service.transaction;

import faraz.wallet.entity.Transaction;
import faraz.wallet.entity.Wallet;
import faraz.wallet.entity.TransactionStatus;
import faraz.wallet.entity.TransactionType;
import faraz.wallet.exception.ApiException;
import faraz.wallet.repository.TransactionRepository;
import faraz.wallet.repository.WalletRepository;
import faraz.wallet.service.SystemLogService;
import faraz.wallet.service.wallet.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransactionCommandService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final WalletService walletService;
    private final SystemLogService systemLogService;

    public TransactionCommandService(
            TransactionRepository transactionRepository,
            WalletRepository walletRepository,
            WalletService walletService,
            SystemLogService systemLogService
    ) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.walletService = walletService;
        this.systemLogService = systemLogService;
    }

    @Transactional
    public void credit(Long userId, BigDecimal amount, String description) {

        Wallet wallet = walletService.getOrCreateWallet(userId);

        Transaction transaction = createBaseTransaction(
                wallet, amount, description, TransactionType.CREDIT
        );

        switch(amount.compareTo(BigDecimal.valueOf(0)))
        {
            case (-1): transaction.setStatus(TransactionStatus.FAILED);

            case(0): transaction.setStatus(TransactionStatus.FAILED);

            case(1): transaction.setStatus(TransactionStatus.SUCCESS);
            wallet.setBalance(wallet.getBalance().add(amount));
        }

        transactionRepository.save(transaction);
        walletRepository.save(wallet);

        if (transaction.getStatus().equals(TransactionStatus.SUCCESS))
        {
            systemLogService.log(
                    "TRANSACTION_CREDIT",
                    wallet.getUser().getPhoneNumber(),
                    "Credit " + amount
            );
        }else {
            systemLogService.log(
                    "TRANSACTION_FAILED",
                    wallet.getUser().getPhoneNumber(),
                    "INVALID_CREDIT(0 or negative) "
            );
        }
    }

    @Transactional
    public void debit(Long userId, BigDecimal amount, String description) {

        Wallet wallet = walletService.getOrCreateWallet(userId);

        Transaction transaction = createBaseTransaction(
                wallet, amount, description, TransactionType.DEBIT
        );

        if (wallet.getBalance().compareTo(amount) < 0) {

            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            systemLogService.log(
                    "TRANSACTION_DEBIT_FAILED",
                    wallet.getUser().getPhoneNumber(),
                    "Insufficient balance"
            );
            throw new ApiException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }else{

        transaction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);


        systemLogService.log(
                "TRANSACTION_DEBIT",
                wallet.getUser().getPhoneNumber(),
                "Debit " + amount
        );
    }
    }

    private Transaction createBaseTransaction(
            Wallet wallet,
            BigDecimal amount,
            String description,
            TransactionType type
    ) {
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setCreatedAt(Instant.now());
        transaction.setStatus(TransactionStatus.FAILED);
        return transaction;
    }
}
