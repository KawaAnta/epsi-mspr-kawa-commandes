package com.kawa.orders.ordersapi.domain.service.order;

import com.kawa.orders.ordersapi.domain.ports.OrderRepository;
import com.kawa.orders.ordersapi.domain.service.order.dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository mockOrderRepository;

    private OrderServiceImpl orderServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        orderServiceImplUnderTest = new OrderServiceImpl(mockOrderRepository);
    }

    @Test
    void testGetAll() {
        // GIVEN
        when(mockOrderRepository.getAll()).thenReturn(List.of(Order.builder().build()));

        // WHEN
        final List<Order> result = orderServiceImplUnderTest.getAll();

        // THEN
        assertThat(result).hasSize(1);
    }

    @Test
    void testGetAll_OrderRepositoryReturnsNoItems() {
        // GIVEN
        when(mockOrderRepository.getAll()).thenReturn(Collections.emptyList());

        // WHEN
        final List<Order> result = orderServiceImplUnderTest.getAll();

        // THEN
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetById() {
        // GIVEN
        Order order = Order.builder().build();
        order.setId(0L);
        when(mockOrderRepository.getById(0L)).thenReturn(order);

        // WHEN
        final Order result = orderServiceImplUnderTest.getById(0L);

        // THEN
        assertThat(orderServiceImplUnderTest.getById(0L)).isEqualTo(order);
    }

    @Test
    void testDeleteById() {
        // WHEN
        orderServiceImplUnderTest.deleteById(0L);

        // THEN
        verify(mockOrderRepository).deleteById(0L);
    }

    @Test
    void testSave() {
        // GIVEN
        final Order order = Order.builder().build();
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order);

        // WHEN
        final Order result = orderServiceImplUnderTest.save(order);

        // THEN
        assertThat(result).isEqualTo(order);
    }
}
