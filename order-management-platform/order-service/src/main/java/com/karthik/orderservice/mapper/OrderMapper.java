package com.karthik.orderservice.mapper;

import com.karthik.orderservice.dto.OrderRequestDTO;
import com.karthik.orderservice.dto.OrderResponseDTO;
import com.karthik.orderservice.model.Order;
import com.karthik.orderservice.model.OrderStatus;

import java.util.UUID;

public class OrderMapper {

    public static Order toEntity(OrderRequestDTO request) {

        return Order.builder()
                .id(UUID.randomUUID().toString())
                .customerId(request.getCustomerId())
                .product(request.getProduct())
                .orderType(request.getOrderType())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .status(OrderStatus.CREATED)
                .build();
    }

    public static OrderResponseDTO toResponse(Order order) {

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .status(order.getStatus().name())
                .build();
    }
}