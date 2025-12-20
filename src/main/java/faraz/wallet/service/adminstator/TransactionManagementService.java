package faraz.wallet.service.adminstator;

import faraz.wallet.dto.response.TransactionResponse;
import faraz.wallet.entity.Transaction;
import faraz.wallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionManagementService {

    private final TransactionRepository transactionRepository;


    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponse toResponse(Transaction t) {
        return new TransactionResponse(
                t.getAmount(),
                t.getType().name(),
                t.getStatus(),
                t.getDescription(),
                t.getCreatedAt()
        );
    }
}
