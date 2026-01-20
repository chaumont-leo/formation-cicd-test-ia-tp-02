package cicd;

import com.devops.cicd.PasswordPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class PasswordPolicyTest {

    @Test
    void weakIfLessThan8Chars() {
        assertFalse(PasswordPolicy.isStrong("Ab1!"));
    }

    @Test
    void strongIfHasUpperLowerDigitAndSpecialAndMin8() {
        assertTrue(PasswordPolicy.isStrong("Azerty1!"));
    }

    @Test
    void notStrongIfNoSpecialChar() {
        assertFalse(PasswordPolicy.isStrong("Azerty12"));
    }

    @Test
    void notStrongIfNoDigit() {
        assertFalse(PasswordPolicy.isStrong("Azerty!!"));
    }

    @Test
    void notStrongIfNoUppercase() {
        assertFalse(PasswordPolicy.isStrong("azerty1!"));
    }

    @Test
    void notStrongIfNoLowercase() {
        assertFalse(PasswordPolicy.isStrong("AZERTY1!"));
    }

    @Test
    void notStrongIfNullOrBlank() {
        assertFalse(PasswordPolicy.isStrong(null));
        assertFalse(PasswordPolicy.isStrong(""));
        assertFalse(PasswordPolicy.isStrong("        "));
    }

    @Test
    @DisplayName("Cas Nominal - Mot de passe fort")
    void testStrongPassword() {
        assertTrue(PasswordPolicy.isStrong("P@ssword24"));
    }

    @Test
    @DisplayName("Cas Limite - Longueur exacte de 8")
    void testExactLength() {
        assertTrue(PasswordPolicy.isStrong("Ab1!5678"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Ab1!567",      // Trop court (< 8)
            "p@ssword24",   // Pas de majuscule
            "P@ssword!",    // Pas de chiffre
            "Password24"    // Pas de car. spécial
    })
    @DisplayName("Cas d'Erreur - Critères non respectés")
    void testWeakPasswords(String password) {
        assertFalse(PasswordPolicy.isStrong(password));
    }
}
