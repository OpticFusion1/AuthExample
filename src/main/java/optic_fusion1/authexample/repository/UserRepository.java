package optic_fusion1.authexample.repository;

import optic_fusion1.authexample.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
