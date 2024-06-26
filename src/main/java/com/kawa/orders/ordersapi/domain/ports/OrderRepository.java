package com.kawa.orders.ordersapi.domain.ports;

import com.kawa.orders.ordersapi.domain.service.order.dto.Order;
import java.util.List;

/**
 * Repository de la couche domaine.
 */
public interface OrderRepository {

    /**
     * Lecture de toutes les commandes renseignées en BDD.
     * @return la liste de toutes les commandes.
     */
    List<Order> getAll();

    /**
     * Lecture d'une commande par ID.
     * @param id identifiant de la commande.
     * @return une commande.
     */
    Order getById(Long id);

    /**
     * Supprime une commande à travers son ID.
     * @param id identifiant de la commande.
     */
    void deleteById(Long id);

    /**
     * Enregistre ou modifie une commande en BDD.
     * @param order la commande.
     */
    Order save(Order order);
}
