package se.visionmate.test.service;

import se.visionmate.test.api.users.CreateUserRequest;
import se.visionmate.test.api.users.UpdateUserRequest;
import se.visionmate.test.exception.ResourceNotFoundException;
import se.visionmate.test.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);
    void deleteUserById(Long id) throws ResourceNotFoundException;
    User createUser(CreateUserRequest request) throws ResourceNotFoundException;
    List<User> findAllUsers();
    User updateUser(Long id, UpdateUserRequest request) throws ResourceNotFoundException;

}
