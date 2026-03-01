package com.karthik.ordermanagement.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderId;

    private String customerId;

    private String product;

    private OrderType orderType;

    private double quantity;

    private double price;

    private OrderStatus status;

    private LocalDateTime createdAt;
}