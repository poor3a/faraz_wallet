package faraz.wallet.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LoginWithPasswordRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

    public LoginWithPasswordRequest() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
