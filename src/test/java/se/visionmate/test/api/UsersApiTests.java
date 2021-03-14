package se.visionmate.test.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import se.visionmate.test.api.users.CreateUserRequest;
import se.visionmate.test.api.users.UserDTO;
import se.visionmate.test.api.users.UsersController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class UsersApiTests {

	@Autowired
	private UsersController usersController;

	@WithMockUser(username="admin", authorities = {"CREATE_USERS"})
	@Test
	void createUsersPermissionsRequiredWhenCreatingUsers() {
		CreateUserRequest request = new CreateUserRequest().setUsername("tom").setPassword("password").setRoleId(1l);
		UserDTO result = usersController.create(request);
		assertThat(result.getId()).isNotNull();
	}

	@WithMockUser(username="admin", authorities = {"LIST_USERS"})
	@Test
	void listUsersPermissionsRequiredToListUsers() {
		assertThat(usersController.list()).isNotNull();
	}

	@WithMockUser(username="admin", authorities = {"CREATE_USERS"})
	@Test
	void exceptionWhenTryingToListUsersWithoutPermissions() {
		assertThrows(AccessDeniedException.class, () -> usersController.list());
	}

	@WithAnonymousUser
	@Test
	void anonymousUserCantCreateUsers() {
		CreateUserRequest request = new CreateUserRequest().setUsername("jerry").setPassword("password").setRoleId(1l);
		assertThrows(AccessDeniedException.class, () -> usersController.create(request));
	}

}
