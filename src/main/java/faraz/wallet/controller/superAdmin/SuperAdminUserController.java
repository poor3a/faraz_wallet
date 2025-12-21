package faraz.wallet.controller.superAdmin;

import faraz.wallet.dto.request.AdminCreateUserRequest;
import faraz.wallet.dto.request.UpdateUserRequest;
import faraz.wallet.dto.response.AdminUserResponse;
import faraz.wallet.dto.response.UserResponse;
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
@RequestMapping("/super_admin/users")
public class SuperAdminUserController {

    private final UserManagementService userManagementService;


    @GetMapping
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {
        return ResponseEntity.ok(
                userManagementService.getAllUserResponses()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody AdminCreateUserRequest request
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
    public ResponseEntity<AdminUserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request
    ) {
        AdminUserResponse response = userManagementService.updateUser(
                userId,
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getEnabled(),
                request.getRole()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/disable")
    public void disableUser(@PathVariable Long userId) {
        userManagementService.disableUser(userId);
    }
}
