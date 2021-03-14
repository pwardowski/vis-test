package se.visionmate.test.api.users;

import lombok.Value;
import se.visionmate.test.api.roles.RoleDTO;
import se.visionmate.test.model.User;

@Value
public class UserDTO {

    Long id;
    String username;
    RoleDTO role;

    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getUsername(), RoleDTO.from(user.getRole()));
    }
}
