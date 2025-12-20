package faraz.wallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class UserResponse {

    private Long id;
    private String phoneNumber;
    private boolean enabled;
    private String role;



}
