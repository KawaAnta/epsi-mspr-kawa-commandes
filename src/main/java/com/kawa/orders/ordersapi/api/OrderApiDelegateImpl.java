package com.kawa.orders.ordersapi.api;


import com.kawa.orders.generated.api.model.OrderDto;
import com.kawa.orders.generated.api.server.OrdersApiDelegate;
import com.kawa.orders.ordersapi.domain.service.order.OrderService;
import com.kawa.orders.ordersapi.domain.service.order.dto.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Délégué API des commandes.
 * Assure la connexion entre l'API Gateway générale et les micro-services des commandes.
 */
@Component
public class OrderApiDelegateImpl implements OrdersApiDelegate {
    private final OrderService orderService;

    public OrderApiDelegateImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        try {
            final List<Order> OrderList = orderService.getAll();
            final List<OrderDto> OrderDtoList = new ArrayList<>(OrderList.size());

            for (final Order order : OrderList) {
                OrderDtoList.add(mapToDto(order));
            }

            return ResponseEntity.ok(OrderDtoList);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<OrderDto> getOrderById(Long id) {
        try {
            Order order = orderService.getById(id);

            if (order.getId() == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(mapToDto(order));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Void> deleteOrderById(Long id) {
        try {
            Order order = orderService.getById(id);

            if (order.getId() == null) {
                return ResponseEntity.notFound().build();
            }

            orderService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Override
    public ResponseEntity<String> createOrder(@Valid OrderDto orderDto) {
        try {
            Order order = new Order();
            order.setUserId(orderDto.getUserId());
            order.setCreatedAt(LocalDateTime.now());
            order.setProductIds(orderDto.getProductsIds());
            Order savedOrder = orderService.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder.getId().toString());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur inattendue.");
        }
    }

    @Override
    public ResponseEntity<Void> updateOrder(Long id, OrderDto OrderDto) {
        try {
            // Récupérer le produit existant avec l'ID spécifié
            Order Order = orderService.getById(id);

            // Récupérer la liste de tous les IDs des produits existants
            List<Order> OrderList = orderService.getAll();
            List<Long> ids = new ArrayList<>();
            for (Order existingOrder : OrderList) {
                ids.add(existingOrder.getId());
            }

            // Vérifier si l'ID spécifié ne correspond à aucun des IDs existants
            if (!ids.contains(id)) {
                return ResponseEntity.notFound().build();
            }

            // Copier les propriétés non null de OrderDto vers Order
            copyProperties(OrderDto, Order);

            // Enregistrer les modifications
            orderService.save(Order);

            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    /**
     * Vérifie les champs mentionnés dans la requête API pour les modifier dans l'objet commande.
     *
     * @param dto la source
     * @param target l'objet produit en BDD
     */
    private void copyProperties(OrderDto dto, Order target) throws IllegalAccessException {
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(dto);
            if (value != null) {
                Field targetField;
                try {
                    targetField = target.getClass().getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                    targetField.set(target, value);
                } catch (NoSuchFieldException ignored) {
                }
            }
        }
    }

    @NotNull
    private static OrderDto mapToDto(@NotNull final Order order) {
        return new OrderDto(
                order.getUserId(),
                order.getProductIds()
        );
    }
}
