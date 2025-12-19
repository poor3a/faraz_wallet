package faraz.wallet.repository;

import faraz.wallet.entity.Role;
import faraz.wallet.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByType(RoleType type);
}
