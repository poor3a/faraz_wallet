package faraz.wallet.controller.user;

import faraz.wallet.dto.request.TransactionRequest;
import faraz.wallet.dto.response.TransactionResponse;
import faraz.wallet.dto.response.TransactionSummaryResponse;
import faraz.wallet.security.CustomUserDetails;
import faraz.wallet.service.transaction.TransactionCommandService;
import faraz.wallet.service.transaction.TransactionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/transactions")
public class UserTransactionController {

    private final TransactionService transactionService;
    private final TransactionCommandService transactionCommandService;

    public UserTransactionController(
            TransactionService transactionService,
            TransactionCommandService transactionCommandService
    ) {
        this.transactionService = transactionService;
        this.transactionCommandService = transactionCommandService;
    }

    @GetMapping
    public List<TransactionResponse> getMyTransactions(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return transactionService.getMyTransactions(user.getId());
    }

    @GetMapping("/summary")
    public TransactionSummaryResponse getMyTransactionSummary(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return transactionService.getMyTransactionSummary(user.getId());
    }

    @PostMapping("/credit")
    public void credit(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody TransactionRequest request
    ) {
        transactionCommandService.credit(
                user.getId(),
                request.getAmount(),
                request.getDescription()
        );
    }

    @PostMapping("/debit")
    public void debit(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody TransactionRequest request
    ) {
        transactionCommandService.debit(
                user.getId(),
                request.getAmount(),
                request.getDescription()
        );
    }
}
