package faraz.wallet.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AdminUpdateUserRequest {

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format"
    )
    private String email;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private String role;

}
