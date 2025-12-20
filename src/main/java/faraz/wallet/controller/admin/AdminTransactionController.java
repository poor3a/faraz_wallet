package faraz.wallet.controller.admin;

import faraz.wallet.dto.response.TransactionResponse;
import faraz.wallet.service.adminstator.TransactionManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/transactions")
public class AdminTransactionController {

    private final TransactionManagementService transactionManagementService;

    public AdminTransactionController(TransactionManagementService transactionManagementService) {
        this.transactionManagementService = transactionManagementService;
    }

    @GetMapping
    public List<TransactionResponse> getAllTransactions() {
        return transactionManagementService.getAllTransactions();
    }
}
