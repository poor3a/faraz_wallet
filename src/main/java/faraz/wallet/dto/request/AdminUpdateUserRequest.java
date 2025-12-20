package faraz.wallet.dto.request;

import lombok.Getter;

@Getter
public class AdminUpdateUserRequest {

    private String email;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private String role;

}
