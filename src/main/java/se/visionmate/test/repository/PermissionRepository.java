package se.visionmate.test.repository;

import org.springframework.data.repository.CrudRepository;
import se.visionmate.test.model.Permission;

import java.util.Collection;
import java.util.List;

public interface PermissionRepository extends CrudRepository<Permission, Long> {

    Permission findByName(String name);
    List<Permission> findByNameIn(Collection<String> permissions);

}
