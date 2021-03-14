package se.visionmate.test.api.users;

import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.visionmate.test.model.User;
import se.visionmate.test.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @PreAuthorize("hasAuthority('DELETE_USERS')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @SneakyThrows
    @PreAuthorize("hasAuthority('EDIT_USERS')")
    @PatchMapping("{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UpdateUserRequest request){

        User updatedUser = userService.updateUser(id, request);
        return UserDTO.from(updatedUser);
    }

    @SneakyThrows
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    @PostMapping
    public UserDTO create(@RequestBody CreateUserRequest request){

        User createdUser = userService.createUser(request);
        return UserDTO.from(createdUser);

    }

    @PreAuthorize("hasAuthority('LIST_USERS')")
    @GetMapping
    public List<UserDTO> list() {

        return userService.findAllUsers()
                .stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());
    }

}