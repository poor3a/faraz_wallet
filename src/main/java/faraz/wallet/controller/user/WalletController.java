package faraz.wallet.controller.user;

import faraz.wallet.dto.response.WalletResponse;
import faraz.wallet.entity.Wallet;
import faraz.wallet.security.CustomUserDetails;
import faraz.wallet.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/wallet")
public class WalletController {

    private final WalletService walletService;


    @GetMapping
    public ResponseEntity<WalletResponse> getMyWallet(
            @AuthenticationPrincipal CustomUserDetails userDetails
    )
    {
        Wallet wallet = walletService.getOrCreateWallet(userDetails.getId());
        return ResponseEntity.ok(
                new WalletResponse(
                        wallet.getAccountId(),
                        wallet.getBalance()
                )
        );
    }
}
