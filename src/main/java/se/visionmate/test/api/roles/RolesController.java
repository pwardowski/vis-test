package se.visionmate.test.api.roles;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import se.visionmate.test.exception.ResourceNotFoundException;
import se.visionmate.test.model.Role;
import se.visionmate.test.repository.PermissionRepository;
import se.visionmate.test.repository.RoleRepository;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.EMPTY_LIST;

@RolesAllowed("ADMIN")
@RestController
@RequestMapping(path = "roles")
public class RolesController {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RolesController(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @PostMapping
    public RoleDTO create(@RequestBody CreateRoleRequest request){

        List<String> permissions = Optional.ofNullable(request.getPermissions()).orElse(EMPTY_LIST);

        Role role = new Role()
                .setName(request.getName())
                .setPermissions(permissionRepository.findByNameIn(permissions));

        return RoleDTO.from(roleRepository.save(role));
    }

    @SneakyThrows
    @PatchMapping("{id}")
    public RoleDTO update(@PathVariable Long id, @RequestBody UpdateRoleRequest request){

        List<String> newPermissions = Optional.ofNullable(request.getPermissions()).orElse(EMPTY_LIST);

        Role role = roleRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new)
                .setName(request.getName())
                .setPermissions(permissionRepository.findByNameIn(newPermissions));

        return RoleDTO.from(roleRepository.save(role));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        roleRepository.deleteById(id);
    }

    @GetMapping
    public Collection<RoleDTO> list() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .map(RoleDTO::from)
                .collect(Collectors.toList());
    }

}