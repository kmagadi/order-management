package com.karthik.orderservice.analytics;

import com.karthik.orderservice.model.Order;
import com.karthik.orderservice.model.OrderStatus;

import java.util.List;
import java.util.Map;

public interface OrderAnalytics {

    double totalOrderAmount(List<Order> orders);

    Map<String, Long> buyVsSell(List<Order> orders);

    String topCustomer(List<Order> orders);

    Map<OrderStatus, Long> groupByStatus(List<Order> orders);
}