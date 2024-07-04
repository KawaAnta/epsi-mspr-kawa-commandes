package com.kawa.orders.ordersapi.message;

import com.kawa.orders.ordersapi.dto.OrderResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderMessageProducerTest {

    @Mock
    private RabbitTemplate mockRabbitTemplate;

    private OrderMessageProducer orderMessageProducerUnderTest;

    @BeforeEach
    void setUp() {
        orderMessageProducerUnderTest = new OrderMessageProducer(mockRabbitTemplate);
    }

    @Test
    void testSendMessage() {
        // GIVEN
        final OrderResponseDTO orderResponseDTO = new OrderResponseDTO(List.of(0L));

        // WHEN
        orderMessageProducerUnderTest.sendMessage(orderResponseDTO);

        // THEN
        verify(mockRabbitTemplate).convertAndSend("orderQueue", new OrderResponseDTO(List.of(0L)));
    }

    @Test
    void testSendMessage_RabbitTemplateThrowsAmqpException() {
        // GIVEN / WHEN
        final OrderResponseDTO orderResponseDTO = new OrderResponseDTO(List.of(0L));
        doThrow(AmqpException.class).when(mockRabbitTemplate).convertAndSend("orderQueue",
                new OrderResponseDTO(List.of(0L)));

        // THEN
        assertThatThrownBy(() -> orderMessageProducerUnderTest.sendMessage(orderResponseDTO))
                .isInstanceOf(AmqpException.class);
    }
}
