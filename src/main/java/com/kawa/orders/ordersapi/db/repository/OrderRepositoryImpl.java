package com.kawa.orders.ordersapi.db.repository;

import com.kawa.orders.ordersapi.db.models.OrderDb;
import com.kawa.orders.ordersapi.db.port.mapper.OrderMapper;
import com.kawa.orders.ordersapi.domain.ports.OrderRepository;
import com.kawa.orders.ordersapi.domain.service.order.dto.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implémentation du repository de la couche domaine.
 * Elle permet d'implémenter les méthodes assurant les opérations métier - DB.
 * ELle implémente les mapper assurant la transformation des objets DB - DTO.
 */
@Component
@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderDbRepository dbRepository;
    private final OrderMapper mapper;
    static Logger logger = Logger.getLogger(OrderRepositoryImpl.class.getName());

    @Override
    @Transactional
    public List<Order> getAll() {
        List<OrderDb> orders = dbRepository.findAll();
        return orders.stream().map(mapper::mapToDomain).toList();
    }

    @Override
    public Order getById(Long id) {
        OrderDb orderDb = dbRepository.findById(id).orElse(null);
        return mapper.mapToDomain(orderDb);
    }

    @Override
    public void deleteById(Long id) {
        try {
            dbRepository.deleteById(id);
        } catch (Exception exception) {
            logger.warning(exception.getMessage());
        }
    }

    @Override
    public Order save(Order order) {
        try {
            return mapper.mapToDomain(dbRepository.save(mapper.mapFromDomain(order)));
        } catch (Exception exception) {
            logger.warning(exception.getMessage());
        }
        return null;
    }
}
