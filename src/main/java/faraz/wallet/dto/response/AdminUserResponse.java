package faraz.wallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class AdminUserResponse {

    private Long id;
    private String phoneNumber;
    private String email;
    private String role;
    private boolean enabled;
    private Instant createdAt;



}
