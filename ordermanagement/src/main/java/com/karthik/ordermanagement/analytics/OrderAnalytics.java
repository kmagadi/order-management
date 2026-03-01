package com.karthik.ordermanagement.analytics;

import com.karthik.ordermanagement.model.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderAnalytics {

    public static double totalAmount(List<Order> orders) {
        return orders.stream()
                .mapToDouble(o -> o.getPrice() * o.getQuantity())
                .sum();
    }

    public static Map<String, Long> buyVsSell(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getOrderType().name(),
                        Collectors.counting()
                ));
    }

    public static String topCustomer(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomerId,
                        Collectors.summingDouble(o -> o.getPrice() * o.getQuantity())
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No orders");
    }

    public static Map<OrderStatus, Long> groupByStatus(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getStatus,
                        Collectors.counting()
                ));
    }
}