package se.visionmate.test.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.visionmate.test.api.users.CreateUserRequest;
import se.visionmate.test.api.users.UpdateUserRequest;
import se.visionmate.test.exception.ResourceNotFoundException;
import se.visionmate.test.model.Role;
import se.visionmate.test.model.User;
import se.visionmate.test.repository.RoleRepository;
import se.visionmate.test.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceBean implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceBean(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        userRepository.delete(user);
    }

    @Override
    public User createUser(CreateUserRequest request) throws ResourceNotFoundException {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(ResourceNotFoundException::new);

        User user = new User()
                .setUsername(request.getUsername())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRole(role);

        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
               .collect(Collectors.toList());
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) throws ResourceNotFoundException {

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(ResourceNotFoundException::new);

        User user = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new)
                .setUsername(request.getUsername())
                .setRole(role);

        return userRepository.save(user);
    }

}
