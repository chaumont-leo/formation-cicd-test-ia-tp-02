package cicd.user;

import com.devops.cicd.user.Role;
import com.devops.cicd.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testAdminAccess() {
        User user = new User("alice@gmail.com", "Azerty1!", Role.ADMIN);
        assertTrue(user.canAccessAdminArea());
    }

    @Test
    void testUserAccess() {
        User user = new User("alice@gmail.com", "Azerty1!", Role.USER);
        assertFalse(user.canAccessAdminArea());
    }

    @Test
    void testNullRole() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = new User("alice@gmail.com", "Azerty1!", null);
        });
    }
}
