package faraz.wallet.dto.request;

import faraz.wallet.enums.RoleType;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format"
    )
    private String email;

    private String firstName;
    private String lastName;
    private Boolean enabled;
    private RoleType role;

}
