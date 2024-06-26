package com.kawa.orders.ordersapi.db.models;

import com.kawa.utils.LongListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDERS")
public class OrderDb {
    @Id
    @Column(name = "ORDER_ID")
    @SequenceGenerator(name = "orderIdGenerator", sequenceName = "ORDER_SEQUENCE",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderIdGenerator")
    private Long id;

    @Column(name = "ORDER_USER_ID")
    private Long userId;

    @Column(name = "ORDER_CREATED_AT")
    private LocalDateTime createdAt;

    @Convert(converter = LongListConverter.class)
    @Column(name = "ORDER_PRODUCT_IDS")
    private List<Long> productIds;
}
