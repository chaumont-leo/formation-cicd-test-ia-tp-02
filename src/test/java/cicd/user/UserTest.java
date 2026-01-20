package cicd.user;

import com.devops.cicd.user.Role;
import com.devops.cicd.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Nested
    @DisplayName("Cas Nominaux - Inscriptions réussies")
    class SuccessTests {

        @Test
        @DisplayName("Inscription d'un membre standard")
        void testRegisterMemberSuccess() {
            // [cite: 12, 13] Création d'un utilisateur avec données conformes
            User user = User.of("test.test@gmail.com", "Azerty1!", Role.USER);

            assertNotNull(user);
            assertEquals("test.test@gmail.com", user.getEmail());
            //  Un rôle MEMBER (USER) ne doit pas avoir accès à l'admin
            assertFalse(user.canAccessAdminArea());
        }

        @Test
        @DisplayName("Inscription d'un administrateur")
        void testRegisterAdminSuccess() {
            User user = User.of("test.test@gmail.com", "Azerty1!", Role.ADMIN);

            assertNotNull(user);
            // [cite: 9, 10] L'accès Admin est réservé au rôle ADMIN
            assertTrue(user.canAccessAdminArea());
        }
    }

    @Nested
    @DisplayName("Cas d'Erreur - Validations et Exceptions")
    class FailureTests {

        @Test
        @DisplayName("Échec : Format d'email invalide")
        void testInvalidEmail() {
            // [cite: 3, 4] Règle : Doit contenir un point après le @
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                    User.of("sdfsdfsdf@sdfsdf", "Azerty1!", Role.USER)
            );
            assertEquals("email must be valid", ex.getMessage());
        }

        @ParameterizedTest
        @CsvSource({
                "azerty1!, USER, 'Minuscules uniquement'",
                "AAzerty1, USER, 'Pas de caractère spécial'",
                "Azertyyy!, USER, 'Pas de chiffre'",
                "Azty1!, ADMIN, 'Longueur < 8 caractères'"
        })
        @DisplayName("Échec : Mot de passe non conforme")
        void testWeakPasswords(String password, Role role, String scenario) {
            // [cite: 6, 7] Règles : >= 8 car., 1 Maj, 1 min, 1 chiffre, 1 car. spécial
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                    User.of("sdfsdfsdf@sdfsdf.com", password, role)
            );
            assertEquals("password must be strong", ex.getMessage());
        }

        @Test
        @DisplayName("Échec : Rôle manquant")
        void testNullRole() {
            // [cite: 9, 10] Règle : Rôle obligatoire (non null)
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                    User.of("sdfsdfsdf@sdfsdf.com", "Azerty1!", null)
            );
            // Note : Correction du message "ole" vers "role" selon les specs
            assertEquals("role must not be null", ex.getMessage());
        }
    }

    @Test
    void testAdminAccess() {
        User user = User.of("alice@gmail.com", "Azerty1!", Role.ADMIN);
        assertTrue(user.canAccessAdminArea());
    }

    @Test
    void testUserAccess() {
        User user = User.of("alice@gmail.com", "Azerty1!", Role.USER);
        assertFalse(user.canAccessAdminArea());
    }

    @Test
    void testNullRole() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = User.of("alice@gmail.com", "Azerty1!", null);
        });
    }
}
