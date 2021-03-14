package se.visionmate.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.visionmate.test.model.Permission;
import se.visionmate.test.model.Role;
import se.visionmate.test.model.User;
import se.visionmate.test.repository.PermissionRepository;
import se.visionmate.test.repository.RoleRepository;
import se.visionmate.test.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@Transactional
public class TestApplicationStarter implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public TestApplicationStarter(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }
    
    @Override
    public void run(String... args) {
        createRolesAndPermissions();
        createAdmin();
    }

    private void createRolesAndPermissions() {
        List<Permission> usersApi = createUsersApiPermissions();
        List<Permission> rolesApi = createRolesApiPermissions();

        List<Permission> adminPermissions = new ArrayList<>();
        adminPermissions.addAll(rolesApi);
        adminPermissions.addAll(usersApi);

        createRoleIfNotFound("ROLE_ADMIN", adminPermissions);
    }

    private List<Permission> createRolesApiPermissions() {
        return Arrays.asList(
                createPermissionIfNotFound("LIST_ROLES"),
                createPermissionIfNotFound("CREATE_ROLES"),
                createPermissionIfNotFound("DELETE_ROLES"),
                createPermissionIfNotFound("EDIT_ROLES")
        );
    }

    private List<Permission> createUsersApiPermissions() {
        return Arrays.asList(
                createPermissionIfNotFound("LIST_USERS"),
                createPermissionIfNotFound("CREATE_USERS"),
                createPermissionIfNotFound("DELETE_USERS"),
                createPermissionIfNotFound("EDIT_USERS")
        );
    }

    Permission createPermissionIfNotFound(String name) {

        Permission permission = permissionRepository.findByName(name);
        if (permission == null) {
            permission = permissionRepository.save(new Permission()
                    .setName(name));
        }
        return permission;
    }

    Role createRoleIfNotFound(String name, Collection<Permission> permissions) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = roleRepository.save(new Role()
                     .setName(name)
                     .setPermissions(permissions));
        }
        return role;
    }

    private void createAdmin() {

        if(userRepository.findByUsername("admin").isPresent())
            return;

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        User admin =  new User()
                .setUsername("admin")
                .setPassword(passwordEncoder.encode("admin"))
                .setRole(adminRole);

        userRepository.save(admin);
    }

}
