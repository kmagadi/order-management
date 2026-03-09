package com.karthik.orderservice.service.impl;

import com.karthik.orderservice.model.Order;
import com.karthik.orderservice.model.OrderStatus;
import com.karthik.orderservice.repository.OrderRepository;
import com.karthik.orderservice.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderRepository repository;

    @Override
    public double totalOrderAmount() {

        return repository.findAll()
                .stream()
                .mapToDouble(o -> o.getPrice() * o.getQuantity())
                .sum();
    }

    @Override
    public Map<String, Long> buyVsSell() {

        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        o -> o.getOrderType().name(),
                        Collectors.counting()
                ));
    }

    @Override
    public String topCustomer() {

        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomerId,
                        Collectors.summingDouble(o -> o.getPrice() * o.getQuantity())
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }

    @Override
    public Map<String, Long> groupByStatus() {

        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        o -> o.getStatus().name(),
                        Collectors.counting()
                ));
    }
}