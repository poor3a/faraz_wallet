package faraz.wallet.repository;

import faraz.wallet.entity.User;
import faraz.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUser(User user);

    Optional<Wallet> findByAccountId(String accountId);
}
