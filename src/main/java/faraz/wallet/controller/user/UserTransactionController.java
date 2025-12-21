package faraz.wallet.controller.user;

import faraz.wallet.dto.request.TransactionRequest;
import faraz.wallet.dto.response.TransactionResponse;
import faraz.wallet.dto.response.TransactionSummaryResponse;
import faraz.wallet.security.CustomUserDetails;
import faraz.wallet.service.transaction.TransactionCommandService;
import faraz.wallet.service.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/transactions")
public class UserTransactionController {

    private final TransactionService transactionService;
    private final TransactionCommandService transactionCommandService;



    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getMyTransactions(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(
                transactionService.getMyTransactions(userDetails.getId())
        );
    }

    @GetMapping("/summary")
    public ResponseEntity<TransactionSummaryResponse> getMyTransactionSummary(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(
                transactionService.getMyTransactionSummary(userDetails.getId())
        );
    }

    @PostMapping("/credit")
    public ResponseEntity<Void> credit(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody TransactionRequest request
    ) {
        transactionCommandService.credit(
                userDetails.getId(),
                request.getAmount(),
                request.getDescription()
        );

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/debit")
    public ResponseEntity<Void> debit(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody TransactionRequest request
    ) {
        transactionCommandService.debit(
                userDetails.getId(),
                request.getAmount(),
                request.getDescription()
        );

        return ResponseEntity.noContent().build();
    }
}
