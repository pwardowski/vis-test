package se.visionmate.test.api.roles;

import lombok.Value;
import se.visionmate.test.model.Role;

@Value
public class RoleDTO {
    Long id;
    String name;

    public static RoleDTO from(Role role) {
        return new RoleDTO(role.getId(), role.getName());
    }
}