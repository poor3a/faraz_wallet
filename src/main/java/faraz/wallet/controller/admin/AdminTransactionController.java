package faraz.wallet.controller.admin;

import faraz.wallet.dto.response.AdminUserWalletTransactionsResponse;
import faraz.wallet.service.adminstator.TransactionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/transactions")
@RequiredArgsConstructor
public class AdminTransactionController {

    private final TransactionManagementService transactionManagementService;


    @GetMapping
    public ResponseEntity<List<AdminUserWalletTransactionsResponse>> getAllUsersTransactions() {

        List<AdminUserWalletTransactionsResponse> response =
                transactionManagementService.getAllUsersTransactions();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{phoneNumber}")
    public ResponseEntity<AdminUserWalletTransactionsResponse> getUserTransactions(
            @PathVariable String phoneNumber
    ) {
        AdminUserWalletTransactionsResponse response =
                transactionManagementService.getUserTransactions(phoneNumber);

        return ResponseEntity.ok(response);
    }
}
