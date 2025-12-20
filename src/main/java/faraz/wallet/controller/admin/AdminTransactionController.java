package faraz.wallet.controller.admin;

import faraz.wallet.dto.response.TransactionResponse;
import faraz.wallet.service.adminstator.TransactionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/transactions")
public class AdminTransactionController {

    private final TransactionManagementService transactionManagementService;



    @GetMapping
    public List<TransactionResponse> getAllTransactions()
    {
        return transactionManagementService.getAllTransactions();
    }
}
