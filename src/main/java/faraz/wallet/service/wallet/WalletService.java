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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final SystemLogService systemLogService;



    public Wallet getOrCreateWallet(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found")
                );

        return walletRepository.findByUser(user)
                .orElseGet(() -> {

                    Wallet wallet = new Wallet();
                    wallet.setUser(user);
                    wallet.setAccountId(UUID.randomUUID().toString());
                    wallet.setBalance(BigDecimal.valueOf(0L));
                    wallet.setCreatedAt(Instant.now());

                    Wallet savedWallet = walletRepository.save(wallet);

                    systemLogService.log(
                            "WALLET_CREATED",
                            user.getPhoneNumber(),
                            "Wallet created with accountId=" + savedWallet.getAccountId()
                    );

                    return savedWallet;
                });
    }

    public BigDecimal getWalletBalance(Long userId) {

        Wallet wallet = getOrCreateWallet(userId);

        systemLogService.log(
                "WALLET_BALANCE_VIEW",
                wallet.getUser().getPhoneNumber(),
                "Wallet balance accessed"
        );

        return wallet.getBalance();
    }
}
