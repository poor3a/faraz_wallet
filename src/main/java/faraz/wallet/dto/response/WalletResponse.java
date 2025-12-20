package faraz.wallet.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class WalletResponse {

    private final String accountId;
    private final BigDecimal balance;



}
