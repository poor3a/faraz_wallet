package faraz.wallet.service.user;

import faraz.wallet.dto.response.UserProfileResponse;
import faraz.wallet.entity.User;
import faraz.wallet.exception.ApiException;
import faraz.wallet.repository.UserRepository;
import faraz.wallet.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SystemLogService systemLogService;
    private final PasswordEncoder passwordEncoder;



    public UserProfileResponse updateMyProfile(
            Long userId,
            String email,
            String firstName,
            String lastName
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found")
                );

        if (email != null) user.setEmail(email);
        if (firstName != null) user.setFirstName(firstName);
        if (lastName != null) user.setLastName(lastName);

        userRepository.save(user);

        systemLogService.log(
                "USER_UPDATE_PROFILE",
                user.getPhoneNumber(),
                "User updated own profile"
        );

        return toUserProfileResponse(user);
    }
    private UserProfileResponse toUserProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }


    public void changePassword(Long userId, String oldPassword, String newPassword) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        systemLogService.log(
                "USER_CHANGE_PASSWORD",
                user.getPhoneNumber(),
                "User changed password"
        );
    }
}
