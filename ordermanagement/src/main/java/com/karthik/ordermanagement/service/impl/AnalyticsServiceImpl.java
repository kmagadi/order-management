package com.karthik.ordermanagement.service.impl;

import com.karthik.ordermanagement.analytics.OrderAnalytics;
import com.karthik.ordermanagement.model.OrderStatus;
import com.karthik.ordermanagement.repository.OrderRepository;
import com.karthik.ordermanagement.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderRepository repository;

    @Override
    public double totalOrderAmount() {
        return OrderAnalytics.totalAmount(repository.findAll());
    }

    @Override
    public Map<String, Long> buyVsSell() {
        return OrderAnalytics.buyVsSell(repository.findAll());
    }

    @Override
    public String topCustomer() {
        return OrderAnalytics.topCustomer(repository.findAll());
    }

    @Override
    public Map<OrderStatus, Long> groupByStatus() {
        return OrderAnalytics.groupByStatus(repository.findAll());
    }
}