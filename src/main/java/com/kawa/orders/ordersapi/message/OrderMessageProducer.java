package com.kawa.orders.ordersapi.message;

import com.kawa.orders.ordersapi.config.rabbit.RabbitMQConfig;
import com.kawa.orders.ordersapi.dto.OrderResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public OrderMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderResponseDTO orderResponseDTO) {
        OrderResponseDTO orderMessageDTO = new OrderResponseDTO();
        orderMessageDTO.setProductIds(orderResponseDTO.getProductIds());
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, orderMessageDTO);
    }
}
