package com.karthik.ordermanagement.dto;

import com.karthik.ordermanagement.model.OrderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderRequestDTO {

    @NotBlank
    private String customerId;

    @NotBlank
    private String product;

    private OrderType orderType;

    @Positive
    private double quantity;

    @Positive
    private double price;
}