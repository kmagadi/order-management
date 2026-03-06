package com.karthik.ordermanagement.service.impl;

import com.karthik.ordermanagement.model.*;
import com.karthik.ordermanagement.service.OrderService;
import com.karthik.ordermanagement.validation.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final List<OrderValidator> validators;

    public void validateOrder(Order order) {
        validators.forEach(v -> v.validate(order));
    }

    @Override
    public Order placeOrder(Order order) {
        return null;
    }

    @Override
    public Order getOrder(String id) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }
}