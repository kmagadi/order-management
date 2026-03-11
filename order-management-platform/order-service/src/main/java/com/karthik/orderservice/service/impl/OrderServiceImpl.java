package com.karthik.orderservice.service.impl;

import com.karthik.orderservice.dto.OrderRequestDTO;
import com.karthik.orderservice.dto.OrderResponseDTO;
import com.karthik.orderservice.mapper.OrderMapper;
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

        Order order = OrderMapper.toEntity(request);

        validators.forEach(v -> v.validate(order));

        repository.save(order);

        processor.process(order);

        return OrderMapper.toResponse(order);
    }
}