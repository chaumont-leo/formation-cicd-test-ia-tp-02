package cicd.order;

import com.devops.cicd.order.Order;
import com.devops.cicd.order.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderIntegrationTest {

    private final OrderService orderService = new OrderService();

    @Test
    @DisplayName("Scénario complet : Validation -> Remise -> Priorité -> Arrondi")
    void fullIntegrationScenario() {
        // Quantité 10 * Prix 20.0 = 200.0 (Sous-total)
        // Sous-total >= 100 -> Remise 5% : 200.0 * 0.95 = 190.0
        // Priority true -> + 9.99 : 190.0 + 9.99 = 199.99
        Order order = new Order("INT-100", 10, 20.0, true);

        double result = orderService.computeTotal(order);

        assertEquals(199.99, result);
    }

    @Test
    @DisplayName("Sécurité : Un ID vide bloque tout calcul")
    void shouldFailWhenOrderIsInvalid() {
        Order invalidOrder = new Order("", 10, 20.0, false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.computeTotal(invalidOrder)
        );
        assertEquals("id must not be blank", exception.getMessage());
    }
}