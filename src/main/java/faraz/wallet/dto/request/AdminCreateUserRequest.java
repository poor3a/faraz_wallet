package faraz.wallet.dto.request;

import faraz.wallet.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AdminCreateUserRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,10}$",
            message = "Password must be 5-10 chars and include upper, lower, number, and special character"
    )
    private String password;

    @NotBlank
    private RoleType role;

}
