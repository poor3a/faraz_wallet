package faraz.wallet.service.adminstator;

import faraz.wallet.entity.Role;
import faraz.wallet.Enums.RoleType;
import faraz.wallet.entity.User;
import faraz.wallet.exception.ApiException;
import faraz.wallet.repository.RoleRepository;
import faraz.wallet.repository.UserRepository;
import faraz.wallet.service.SystemLogService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserManagementService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SystemLogService systemLogService;

    public UserManagementService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            SystemLogService systemLogService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.systemLogService = systemLogService;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User createUser(String phone, String password, RoleType roleType) {

        if (userRepository.findByPhoneNumber(phone).isPresent()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "User already exists");
        }

        Role role = roleRepository.findByType(roleType)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Role not found"));

        User user = new User();
        user.setPhoneNumber(phone);
        user.setPassword(password);
        user.setEnabled(true);
        user.setRole(role);
        user.setRegisterDate(Instant.now());

        userRepository.save(user);

        systemLogService.log(
                "USER_CREATED",
                phone,
                "User created by management"
        );

        return user;
    }

    public User updateUser(
            Long userId,
            String email,
            String firstName,
            String lastName,
            Boolean enabled,
            RoleType roleType
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        if (email != null) user.setEmail(email);
        if (firstName != null) user.setFirstName(firstName);
        if (lastName != null) user.setLastName(lastName);
        if (enabled != null) user.setEnabled(enabled);

        if (roleType != null) {
            Role role = roleRepository.findByType(roleType)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Role not found"));
            user.setRole(role);
        }

        userRepository.save(user);

        systemLogService.log(
                "USER_UPDATED",
                user.getPhoneNumber(),
                "User updated by management"
        );

        return user;
    }

    public void disableUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        user.setEnabled(false);
        userRepository.save(user);

        systemLogService.log(
                "USER_DISABLED",
                user.getPhoneNumber(),
                "User disabled by management"
        );
    }

}
