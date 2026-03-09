package com.karthik.orderservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String id;

    private String customerId;

    private String product;

    private OrderType orderType;

    private int quantity;

    private double price;

    private OrderStatus status;

}