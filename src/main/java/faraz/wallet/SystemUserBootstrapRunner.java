package faraz.wallet;

import faraz.wallet.entity.Role;
import faraz.wallet.entity.User;
import faraz.wallet.enums.RoleType;
import faraz.wallet.repository.RoleRepository;
import faraz.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SystemUserBootstrapRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        bootstrapSuperAdmin();
        bootstrapAdmin();
    }

    private void bootstrapSuperAdmin()
    {
        Role role  = roleRepository.findByType(RoleType.SUPER_ADMIN)
                .orElseThrow(()->  new RuntimeException("check role table"));
        if(roleRepository.findByType(RoleType.SUPER_ADMIN).isPresent())
        {
            userRepository.findByPhoneNumber("superadmin")
                    .orElseGet(() -> {
                        User user = new User();
                        user.setPhoneNumber("superadmin");
                        user.setPassword(passwordEncoder.encode("superadmin"));
                        user.setRole(role);
                        user.setEnabled(true);
                        user.setRegisterDate(Instant.now());
                        return userRepository.save(user);
                    });
        }

    }

    private void bootstrapAdmin() {
        Role role  = roleRepository.findByType(RoleType.ADMIN)
                .orElseThrow(()->  new RuntimeException("check role table"));
        userRepository.findByPhoneNumber("admin")
                .orElseGet(() -> {
                    User user = new User();
                    user.setPhoneNumber("admin");
                    user.setPassword(passwordEncoder.encode("admin"));
                    user.setRole(role);
                    user.setEnabled(true);
                    user.setRegisterDate(Instant.now());
                    return userRepository.save(user);
                });
    }
}
