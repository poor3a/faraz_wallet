package faraz.wallet.repository;

import faraz.wallet.entity.Transaction;
import faraz.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByWallet(Wallet wallet);

    List<Transaction> findAllByWalletAndCreatedAtBetween(
            Wallet wallet,
            Instant from,
            Instant to
    );
}
