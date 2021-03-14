package se.visionmate.test.api.roles;

import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class UpdateRoleRequest {
    private String name;
    private List<String> permissions;
}
