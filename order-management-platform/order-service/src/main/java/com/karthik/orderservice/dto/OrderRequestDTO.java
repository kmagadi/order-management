package com.karthik.orderservice.dto;

import com.karthik.orderservice.model.OrderType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {

    @NotNull
    private String customerId;

    @NotNull
    private String product;

    @NotNull
    private OrderType orderType;

    private int quantity;

    private double price;

}