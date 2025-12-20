package faraz.wallet.controller.user;

import faraz.wallet.dto.response.WalletResponse;
import faraz.wallet.security.CustomUserDetails;
import faraz.wallet.service.wallet.WalletService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public WalletResponse getMyWallet(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var wallet = walletService.getOrCreateWallet(userDetails.getId());
        return new WalletResponse(wallet.getAccountId(), wallet.getBalance());
    }
}
