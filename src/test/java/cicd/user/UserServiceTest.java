package cicd.user;

import com.devops.cicd.user.Role;
import com.devops.cicd.user.User;
import com.devops.cicd.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private final UserService userService = new UserService();

    @Test
    @DisplayName("Inscription d'un membre standard")
    void testRegisterMemberSuccess() {
        // [cite: 12, 13] Création d'un utilisateur avec données conformes
        User user = userService.register("test.test@gmail.com", "Azerty1!", Role.USER);

        assertNotNull(user);
        assertEquals("test.test@gmail.com", user.getEmail());
        assertFalse(user.canAccessAdminArea());
    }

    @Test
    @DisplayName("Inscription d'un admin")
    void testRegisterAdminSuccess() {
        // [cite: 12, 13] Création d'un utilisateur avec données conformes
        User user = userService.register("test.test@gmail.com", "Azerty1!", Role.ADMIN);

        assertNotNull(user);
        assertEquals("test.test@gmail.com", user.getEmail());
        assertTrue(user.canAccessAdminArea());
    }

    @Test
    @DisplayName("Inscription en échec")
    void testRegisterMemberFailed() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                userService.register("sdfsdfsdf@sdfsdf", "Azerty1!", Role.USER)
        );
        assertEquals("email must be valid", ex.getMessage());
    }

}