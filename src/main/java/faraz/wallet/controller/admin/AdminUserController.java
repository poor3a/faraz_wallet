package faraz.wallet.controller.admin;

import faraz.wallet.dto.response.UserResponse;
import faraz.wallet.dto.request.AdminCreateUserRequest;
import faraz.wallet.enums.RoleType;
import faraz.wallet.entity.User;
import faraz.wallet.service.adminstator.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserManagementService userManagementService;


    @GetMapping
    public List<User> getAllUsers() {
        return userManagementService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody  AdminCreateUserRequest request
    ) {
        User user = userManagementService.createUser(
                request.getPhoneNumber(),
                request.getPassword(),
                request.getRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponse(
                        user.getId(),
                        user.getPhoneNumber(),
                        user.isEnabled(),
                        user.getRole().getType().toString()
                ));
    }

    @PutMapping("/{userId}")
    public User updateUser(
            @PathVariable Long userId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) RoleType role
    ) {
        return userManagementService.updateUser(
                userId,
                email,
                firstName,
                lastName,
                enabled,
                role
        );
    }

    @PutMapping("/{userId}/disable")
    public void disableUser(@PathVariable Long userId)
    {
        userManagementService.disableUser(userId);
    }
}
