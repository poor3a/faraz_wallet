package faraz.wallet.controller.user;

import faraz.wallet.dto.request.ChangePasswordRequest;
import faraz.wallet.dto.request.UpdateMyProfileRequest;
import faraz.wallet.entity.User;
import faraz.wallet.security.CustomUserDetails;
import faraz.wallet.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @PutMapping("/profile")
    public User updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateMyProfileRequest request
    ) {

        return userService.updateMyProfile(
                userDetails.getId(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName()
        );

    }

    @PutMapping("/change-password")
    public void changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(
                userDetails.getId(),
                request.getOldPassword(),
                request.getNewPassword()
        );
    }
}
