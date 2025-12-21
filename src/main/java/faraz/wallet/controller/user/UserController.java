package faraz.wallet.controller.user;

import faraz.wallet.dto.request.ChangePasswordRequest;
import faraz.wallet.dto.request.UpdateMyProfileRequest;
import faraz.wallet.dto.response.UserProfileResponse;
import faraz.wallet.dto.response.UserResponse;
import faraz.wallet.entity.User;
import faraz.wallet.security.CustomUserDetails;
import faraz.wallet.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdateMyProfileRequest request
    ) {
        UserProfileResponse response = userService.updateMyProfile(
                userDetails.getId(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(
                userDetails.getId(),
                request.getOldPassword(),
                request.getNewPassword()
        );

        return ResponseEntity.noContent().build();
    }
}
