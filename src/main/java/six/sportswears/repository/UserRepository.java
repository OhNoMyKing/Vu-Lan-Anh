package six.sportswears.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.User;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    List<User> findAllByUsernameContainingOrFirstNameContainingOrLastNameContaining(String key1, String key2, String key3);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
