package com.kawa.orders.ordersapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A DTO representing a review message.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMessageDTO {

    private List<Long> productIds;
}
