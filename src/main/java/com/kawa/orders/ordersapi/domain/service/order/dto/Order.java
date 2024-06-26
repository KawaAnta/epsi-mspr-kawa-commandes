package com.kawa.orders.ordersapi.domain.service.order.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe java / DTO représentante de l'objet commande / order
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private List<Long> productIds;
}
