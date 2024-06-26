package com.kawa.orders.ordersapi.api;

import com.kawa.orders.generated.api.model.OrderDto;
import com.kawa.orders.ordersapi.domain.service.order.OrderService;
import com.kawa.orders.ordersapi.domain.service.order.dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderApiDelegateImplTest {

    @Mock
    private OrderService mockOrderService;

    private OrderApiDelegateImpl orderApiDelegateImplUnderTest;

    @BeforeEach
    void setUp() {
        orderApiDelegateImplUnderTest = new OrderApiDelegateImpl(mockOrderService);
    }

    @Test
    void testGetAllOrders() {
        // GIVEN
        final ResponseEntity<List<OrderDto>> expectedResult = new ResponseEntity<>(
                List.of(new OrderDto(0L, List.of(0L))), HttpStatus.OK);

        final List<Order> orders = List.of(Order.builder()
                .id(0L)
                .userId(0L)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .productIds(List.of(0L))
                .build());
        when(mockOrderService.getAll()).thenReturn(orders);

        // WHEN
        final ResponseEntity<List<OrderDto>> result = orderApiDelegateImplUnderTest.getAllOrders();

        // THEN
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllOrders_OrderServiceReturnsNoItems() {
        // GIVEN
        when(mockOrderService.getAll()).thenReturn(Collections.emptyList());

        // WHEN
        final ResponseEntity<List<OrderDto>> result = orderApiDelegateImplUnderTest.getAllOrders();

        // THEN
        assertThat(result).isEqualTo(ResponseEntity.ok(Collections.emptyList()));
    }

    @Test
    void testGetOrderById() {
        // GIVEN
        final ResponseEntity<OrderDto> expectedResult = new ResponseEntity<>(new OrderDto(0L, List.of(0L)),
                HttpStatus.OK);

        final Order order = Order.builder()
                .id(0L)
                .userId(0L)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .productIds(List.of(0L))
                .build();
        when(mockOrderService.getById(0L)).thenReturn(order);

        // WHEN
        final ResponseEntity<OrderDto> result = orderApiDelegateImplUnderTest.getOrderById(0L);

        // THEN
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteOrderById() {
        // GIVEN
        final Order order = Order.builder()
                .id(0L)
                .userId(0L)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .productIds(List.of(0L))
                .build();
        when(mockOrderService.getById(0L)).thenReturn(order);

        // WHEN
        final ResponseEntity<Void> result = orderApiDelegateImplUnderTest.deleteOrderById(0L);

        // THEN
        verify(mockOrderService).deleteById(0L);
    }

    @Test
    void testCreateOrder() {
        // GIVEN
        final OrderDto orderDto = new OrderDto(0L, List.of(0L));
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("0", HttpStatus.CREATED);

        final Order order = Order.builder()
                .id(0L)
                .userId(0L)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .productIds(List.of(0L))
                .build();
        when(mockOrderService.save(any(Order.class))).thenReturn(order);

        // WHEN
        final ResponseEntity<String> result = orderApiDelegateImplUnderTest.createOrder(orderDto);

        // THEN
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateOrder() {
        // GIVEN
        final OrderDto OrderDto = new OrderDto(0L, List.of(0L));

        final Order order = Order.builder()
                .id(0L)
                .userId(0L)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .productIds(List.of(0L))
                .build();
        when(mockOrderService.getById(0L)).thenReturn(order);

        final List<Order> orders = List.of(Order.builder()
                .id(0L)
                .userId(0L)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .productIds(List.of(0L))
                .build());
        when(mockOrderService.getAll()).thenReturn(orders);

        // WHEN
        final ResponseEntity<Void> result = orderApiDelegateImplUnderTest.updateOrder(0L, OrderDto);

        // THEN
        verify(mockOrderService).save(any(Order.class));
    }

    @Test
    void testUpdateOrder_OrderServiceGetAllReturnsNoItems() {
        // GIVEN
        final OrderDto OrderDto = new OrderDto(0L, List.of(0L));

        final Order order = Order.builder()
                .id(0L)
                .userId(0L)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .productIds(List.of(0L))
                .build();
        when(mockOrderService.getById(0L)).thenReturn(order);

        when(mockOrderService.getAll()).thenReturn(Collections.emptyList());

        // WHEN
        final ResponseEntity<Void> result = orderApiDelegateImplUnderTest.updateOrder(0L, OrderDto);

        // THEN
        assertThat(result).isEqualTo(ResponseEntity.notFound().build());
    }
}
