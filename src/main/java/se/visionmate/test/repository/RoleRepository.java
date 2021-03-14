package se.visionmate.test.repository;

import org.springframework.data.repository.CrudRepository;
import se.visionmate.test.model.Role;
import se.visionmate.test.model.User;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
