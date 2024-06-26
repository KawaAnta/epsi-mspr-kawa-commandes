package com.kawa.orders.ordersapi.domain.service.order;

import com.kawa.orders.ordersapi.domain.service.order.dto.Order;
import java.util.List;

/**
 * Service des commandes, contient toutes les méthodes de traitement des données commandes en BDD.
 */
public interface OrderService {

    /**
     * Lecture de tous les commandes présentes en BDD.
     * @return la liste de tous les commandes.
     */
    List<Order> getAll();

    /**
     * Lecture d'une commande à partir de son identifiant.
     * @param id identifiant de la commande.
     * @return un commande.
     */
    Order getById(Long id);

    /**
     * Supprime une commande à travers son identifiant.
     * @param id identifiant de la commande à supprimer.
     */
    void deleteById(Long id);

    /**
     * Enregistre ou modifie une commande en BDD.
     * @param order commande.
     */
    Order save(Order order);

}
