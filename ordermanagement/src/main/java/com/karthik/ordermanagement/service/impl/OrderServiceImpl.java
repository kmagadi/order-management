package com.karthik.ordermanagement.service.impl;

import com.karthik.ordermanagement.exception.OrderNotFoundException;
import com.karthik.ordermanagement.model.*;
import com.karthik.ordermanagement.processor.OrderProcessor;
import com.karthik.ordermanagement.repository.OrderRepository;
import com.karthik.ordermanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderProcessor processor;

    @Override
    public Order placeOrder(Order order) {

        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        repository.save(order);

        processor.process(order);

        return order;
    }

    @Override
    public Order getOrder(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}