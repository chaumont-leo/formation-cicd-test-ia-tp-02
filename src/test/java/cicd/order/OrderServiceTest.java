package cicd.order;

import com.devops.cicd.order.Order;
import com.devops.cicd.order.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @Test
    @DisplayName("Calcul simple : Pas de remise, pas de priorité")
    void computeTotal_NominalCase() {
        Order order = new Order("1", 2, 10.0, false);
        // 2 * 10.0 = 20.0
        assertEquals(20.00, orderService.computeTotal(order));
    }

    @Test
    @DisplayName("Remise : Appliquée exactement au seuil de 100.0")
    void computeTotal_DiscountAtThreshold() {
        Order order = new Order("1", 1, 100.0, false);
        // 100 * 0.95 = 95.00
        assertEquals(95.00, orderService.computeTotal(order));
    }

    @Test
    @DisplayName("Priorité : Ajout des frais fixes de 9.99")
    void computeTotal_WithPriority() {
        Order order = new Order("1", 1, 50.0, true);
        // 50.0 + 9.99 = 59.99
        assertEquals(59.99, orderService.computeTotal(order));
    }

    @Test
    @DisplayName("Arrondi : Arrondi à 2 décimales (ex: .555 -> .56)")
    void computeTotal_CheckRounding() {
        Order order = new Order("1", 1, 10.555, false);
        assertEquals(10.56, orderService.computeTotal(order));
    }
}