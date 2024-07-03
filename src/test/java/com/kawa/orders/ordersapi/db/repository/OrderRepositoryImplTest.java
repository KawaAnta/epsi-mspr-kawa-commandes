package com.kawa.orders.ordersapi.db.repository;

import com.kawa.orders.ordersapi.db.models.OrderDb;
import com.kawa.orders.ordersapi.db.port.mapper.OrderMapper;
import com.kawa.orders.ordersapi.domain.service.order.dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryImplTest {

    @Mock
    private OrderDbRepository mockDbRepository;
    @Mock
    private OrderMapper mockMapper;

    private OrderRepositoryImpl orderRepositoryImplUnderTest;

    @BeforeEach
    void setUp() {
        orderRepositoryImplUnderTest = new OrderRepositoryImpl(mockDbRepository, mockMapper);
    }

    @Test
    void testGetAll() {
        // GIVEN
        final List<OrderDb> orderDbs = List.of(new OrderDb(0L, 0L, LocalDateTime.of(2020, 1, 1, 0, 0, 0), List.of(0L)));
        when(mockDbRepository.findAll()).thenReturn(orderDbs);

        when(mockMapper.mapToDomain(any(OrderDb.class))).thenReturn(Order.builder().build());

        // WHEN
        final List<Order> result = orderRepositoryImplUnderTest.getAll();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(orderDbs.size());
    }

    @Test
    void testGetAll_OrderDbRepositoryReturnsNoItems() {
        // GIVEN
        when(mockDbRepository.findAll()).thenReturn(Collections.emptyList());

        // WHEN
        final List<Order> result = orderRepositoryImplUnderTest.getAll();

        // THEN
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetById_OrderDbRepositoryReturnsAbsent() {
        // GIVEN
        lenient().when(mockDbRepository.findById(0L)).thenReturn(Optional.empty());
        lenient().when(mockMapper.mapToDomain(any(OrderDb.class))).thenReturn(Order.builder().build());

        // WHEN
        final Order result = orderRepositoryImplUnderTest.getById(0L);

        // THEN
        assertThat(result).isNull();
    }

    @Test
    void testDeleteById() {
        // WHEN
        orderRepositoryImplUnderTest.deleteById(0L);

        // THEN
        verify(mockDbRepository).deleteById(0L);
    }

    @Test
    void testSave() {
        // GIVEN
        final Order order = Order.builder().build();
        order.setId(0L);

        final OrderDb orderDb = new OrderDb(0L, 0L, LocalDateTime.of(2020, 1, 1, 0, 0, 0), List.of(0L));
        when(mockMapper.mapFromDomain(any(Order.class))).thenReturn(orderDb);

        final OrderDb orderDb1 = new OrderDb(0L, 0L, LocalDateTime.of(2020, 1, 1, 0, 0, 0), List.of(0L));
        when(mockDbRepository.save(any(OrderDb.class))).thenReturn(orderDb1);

        when(mockMapper.mapToDomain(any(OrderDb.class))).thenReturn(order);

        // WHEN
        final Order result = orderRepositoryImplUnderTest.save(order);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(orderDb.getId());
    }
}
