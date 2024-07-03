package com.kawa.orders.ordersapi.db.port.mapper;

import com.kawa.orders.ordersapi.db.models.OrderDb;
import com.kawa.orders.ordersapi.domain.service.order.dto.Order;
import com.kawa.orders.ordersapi.dto.OrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * Classe assurant les mapping DTO -> DB et DB -> DTO pour les objets Order.
 */
@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface OrderMapper {

    /**
     * Transforme l'objet DB en DTO.
     *
     * @param entity l'objet DB
     * @return l'objet DTO
     */
    Order mapToDomain(OrderDb entity);

    /**
     * Transforme l'objet DTO en DB
     *
     * @param dto l'objet DTO
     * @return l'objet DB
     */
    OrderDb mapFromDomain(Order dto);

    /**
     * Tranforme une liste d'objets DB en liste d'objets DTO
     *
     * @param entities liste des objets DB
     * @return liste des objets DTO
     */
    List<Order> mapListToDomain(List<OrderDb> entities);

    /**
     * Maps un order entity à ReviewResponseDTO.
     *
     * @return mapped ReviewResponseDTO
     */
    default OrderResponseDTO mapToOrderResponseDTO(Order order) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setProductIds(order.getProductIds());
        return responseDTO;
    }
}
