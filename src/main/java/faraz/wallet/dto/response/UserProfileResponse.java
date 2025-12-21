package faraz.wallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
}
