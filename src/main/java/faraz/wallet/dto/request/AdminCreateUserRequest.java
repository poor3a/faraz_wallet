package faraz.wallet.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AdminCreateUserRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
