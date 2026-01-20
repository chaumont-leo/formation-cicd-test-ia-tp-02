package cicd.user;
import com.devops.cicd.user.EmailValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"alice@test.com", "bob.smith@company.io", "a@b.c", " user@test.com "})
    @DisplayName("Cas Nominaux et Limites - Emails valides")
    void testValidEmails(String email) {
        assertTrue(EmailValidator.isValid(email), "L'email devrait être valide : " + email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"alice.com", "alice@@test.com", "alice@test", "@test.com"})
    @DisplayName("Cas d'Erreur - Formats invalides")
    void testInvalidEmailFormats(String email) {
        assertFalse(EmailValidator.isValid(email));
    }

    @Test
    @DisplayName("Cas d'Erreur - Email Null ou Vide")
    void testNullOrEmptyEmail() {
        assertFalse(EmailValidator.isValid(null));
        assertFalse(EmailValidator.isValid(""));
    }
}
