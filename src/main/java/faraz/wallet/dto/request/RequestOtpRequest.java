package faraz.wallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestOtpRequest {

    @NotBlank
    private String phoneNumber;


}
