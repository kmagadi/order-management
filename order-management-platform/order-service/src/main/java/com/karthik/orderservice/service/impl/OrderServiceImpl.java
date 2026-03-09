package com.karthik.orderservice.service.impl;

import com.karthik.orderservice.dto.OrderRequestDTO;
import com.karthik.orderservice.dto.OrderResponseDTO;
import com.karthik.orderservice.model.Order;
import com.karthik.orderservice.model.OrderStatus;
import com.karthik.orderservice.processor.OrderProcessor;
import com.karthik.orderservice.repository.OrderRepository;
import com.karthik.orderservice.service.OrderService;
import com.karthik.orderservice.validation.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderProcessor processor;

    // Spring injects all validators automatically
    private final List<OrderValidator> validators;

    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO request) {

        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .customerId(request.getCustomerId())
                .product(request.getProduct())
                .orderType(request.getOrderType())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .status(OrderStatus.CREATED)
                .build();

        // Run polymorphic validations
        validators.forEach(v -> v.validate(order));

        repository.save(order);

        processor.process(order);

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .status(order.getStatus().name())
                .build();
    }
}