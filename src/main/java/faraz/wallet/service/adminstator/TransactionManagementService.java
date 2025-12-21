package faraz.wallet.service.adminstator;

import faraz.wallet.dto.response.AdminUserWalletTransactionsResponse;
import faraz.wallet.dto.response.TransactionResponse;
import faraz.wallet.entity.User;
import faraz.wallet.entity.Wallet;
import faraz.wallet.exception.ApiException;
import faraz.wallet.service.SystemLogService;
import faraz.wallet.service.transaction.TransactionService;
import faraz.wallet.service.user.UserService;
import faraz.wallet.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionManagementService {

    private final UserManagementService userManagementService;
    private final WalletService walletService;
    private final TransactionService transactionService;
    private final SystemLogService systemLogService;


    public List<AdminUserWalletTransactionsResponse> getAllUsersTransactions() {

        systemLogService.log(
                "ADMIN_TRANSACTIONS_ALL",
                "SYSTEM",
                "Admin requested all users transactions"
        );

        return userManagementService.getAllUsers().stream()
                .map(this::buildUserWalletTransactions)
                .toList();
    }


    public AdminUserWalletTransactionsResponse getUserTransactions(String phoneNumber) {

        User user = userManagementService.getByPhoneNumber(phoneNumber);

        systemLogService.log(
                "ADMIN_TRANSACTIONS_USER",
                phoneNumber,
                "Admin requested transactions for user"
        );

        return buildUserWalletTransactions(user);
    }

    private AdminUserWalletTransactionsResponse buildUserWalletTransactions(User user) {

        Wallet wallet = walletService.findByUser(user)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "Wallet not found for user")
                );

        List<TransactionResponse> transactions =
                transactionService.getMyTransactions(user.getId());

        return new AdminUserWalletTransactionsResponse(
                user.getPhoneNumber(),
                wallet.getAccountId(),
                wallet.getBalance(),
                transactions
        );
    }
}
