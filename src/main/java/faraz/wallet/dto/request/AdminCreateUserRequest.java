package faraz.wallet.dto.request;

import faraz.wallet.Enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdminCreateUserRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

    @NotBlank
    private RoleType role;

}
