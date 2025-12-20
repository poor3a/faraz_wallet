package faraz.wallet.controller.superAdmin;

import faraz.wallet.Enums.RoleType;
import faraz.wallet.entity.User;
import faraz.wallet.service.adminstator.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/super_admin/users")
public class SuperAdminUserController {

    private final UserManagementService userManagementService;


    @GetMapping
    public List<User> getAllUsers() {
        return userManagementService.getAllUsers();
    }

    @PostMapping
    public User createUser(
            @RequestParam String phone,
            @RequestParam String password,
            @RequestParam RoleType role
    ) {
        return userManagementService.createUser(phone, password, role);
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
    public void disableUser(@PathVariable Long userId) {
        userManagementService.disableUser(userId);
    }
}
