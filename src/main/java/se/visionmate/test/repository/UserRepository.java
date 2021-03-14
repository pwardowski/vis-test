package se.visionmate.test.repository;

import org.springframework.data.repository.CrudRepository;
import se.visionmate.test.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
