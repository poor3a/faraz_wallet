package faraz.wallet.controller.admin;

import faraz.wallet.entity.RoleType;
import faraz.wallet.entity.User;
import faraz.wallet.service.adminstator.UserManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserManagementService userManagementService;

    public AdminUserController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

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
