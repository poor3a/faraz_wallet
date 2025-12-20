package faraz.wallet.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LoginWithOtpRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String code;

    public LoginWithOtpRequest() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCode() {
        return code;
    }
}
