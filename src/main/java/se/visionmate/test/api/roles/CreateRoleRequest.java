package se.visionmate.test.api.roles;

import lombok.Data;

import java.util.List;

@Data
public class CreateRoleRequest {
    private String name;
    private List<String> permissions;
}