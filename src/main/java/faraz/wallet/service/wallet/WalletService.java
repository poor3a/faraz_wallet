package faraz.wallet.service.wallet;

import faraz.wallet.entity.User;
import faraz.wallet.entity.Wallet;
import faraz.wallet.exception.ApiException;
import faraz.wallet.repository.UserRepository;
import faraz.wallet.repository.WalletRepository;
import faraz.wallet.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final SystemLogService systemLogService;

    public Optional<Wallet> findByUser(User user) {
        return walletRepository.findByUser(user);
    }
@Transactional
    public Wallet getOrCreateWallet(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found")
                );

        Optional<Wallet> existingWallet = walletRepository.findByUser(user);

        if (existingWallet.isPresent()) {
            Wallet wallet = existingWallet.get();

            systemLogService.log(
                    "WALLET_ACCESSED",
                    user.getPhoneNumber(),
                    "Wallet accessed with accountId=" + wallet.getAccountId()
            );

            return wallet;
        }

        Wallet wallet = createWalletForUser(user);

        systemLogService.log(
                "WALLET_CREATED",
                user.getPhoneNumber(),
                "Wallet created with accountId=" + wallet.getAccountId()
        );

        return wallet;
    }

    private Wallet createWalletForUser(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setAccountId(UUID.randomUUID().toString());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCreatedAt(Instant.now());
        return walletRepository.save(wallet);
    }



}
