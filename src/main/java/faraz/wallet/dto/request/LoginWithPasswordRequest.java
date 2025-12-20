package faraz.wallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginWithPasswordRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;



}
