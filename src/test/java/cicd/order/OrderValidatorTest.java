package cicd.order;

import com.devops.cicd.order.Order;
import com.devops.cicd.order.OrderValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {
    @Test
    @DisplayName("Exception si la commande est null")
    void shouldThrowExceptionWhenOrderIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                OrderValidator.validate(null)
        );
        assertEquals("order must not be null", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @DisplayName("Exception si l'ID est vide ou blanc")
    void shouldThrowExceptionWhenIdIsBlank(String invalidId) {
        Order order = new Order(invalidId, 1, 10.0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                OrderValidator.validate(order)
        );
        assertEquals("id must not be blank", exception.getMessage());
    }

    @Test
    @DisplayName("Exception si la quantité est <= 0")
    void shouldThrowExceptionWhenQuantityIsZeroOrLess() {
        Order order = new Order("ORD-1", 0, 10.0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                OrderValidator.validate(order)
        );
        assertEquals("quantity must be > 0", exception.getMessage());
    }

    @Test
    @DisplayName("Exception si le prix est <= 0")
    void shouldThrowExceptionWhenUnitPriceIsZeroOrLess() {
        Order order = new Order("ORD-1", 10, -5.0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                OrderValidator.validate(order)
        );
        assertEquals("unitPrice must be > 0", exception.getMessage());
    }
}