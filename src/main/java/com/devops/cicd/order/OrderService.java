package com.devops.cicd.order;

/**
 * Service de calcul du montant total d’une commande.
 *
 * Règles métier :
 * 1) Valider la commande
 * 2) Calculer le sous-total : quantity * unitPrice
 * 3) Si le sous-total >= 100 €, appliquer une remise de 5 %
 * 4) Si la commande est prioritaire, ajouter 9.99 € de frais
 * 5) Arrondir le total final à 2 décimales
 *
 * TODO :
 * - implémenter la méthode computeTotal
 */
public class OrderService {

    public static final double PRIORITY_FEE = 9.99;

    public double computeTotal(Order order) {
        try {
            OrderValidator.validate(order);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        double subTotal = order.getUnitPrice() * order.getQuantity();
        if(subTotal >= 100) {
            subTotal = subTotal * 0.95;
        }
        if(order.isPriority()) {
            subTotal = subTotal + PRIORITY_FEE;
        }

        return Math.round(subTotal * 100.0) / 100.0;
    }
}
