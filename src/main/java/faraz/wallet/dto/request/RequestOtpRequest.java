package faraz.wallet.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RequestOtpRequest {

    @NotBlank
    private String phoneNumber;

    public RequestOtpRequest() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
