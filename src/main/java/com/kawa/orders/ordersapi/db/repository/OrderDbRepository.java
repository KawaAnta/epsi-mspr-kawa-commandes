package com.kawa.orders.ordersapi.db.repository;

import com.kawa.orders.ordersapi.db.models.OrderDb;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository de la couche DB, il assure les connexions avec la base de données directement.
 */
@Repository
public interface OrderDbRepository extends CrudRepository<OrderDb, Long> {

    /**
     * Lecture de tous les commandes présentes en BDD.
     * @return la liste de toutes les commandes.
     */
    @NonNull
    List<OrderDb> findAll();
}
