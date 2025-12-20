package faraz.wallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginWithOtpRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String code;



}
