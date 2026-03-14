package com.training_spring_cloud.analytics_service.service.impl;

import com.training_spring_cloud.analytics_service.client.OrderClient;
import com.training_spring_cloud.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderClient orderClient;

    @Override
    public Map<String, Object> getSummary(String role) {

        List<Map<String, Object>> orders = orderClient.getAllOrders(role);

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalOrders", orders.size());

        return summary;
    }

    public Map<String, Long> getBuyVsSell(String role) {

        List<Map<String, Object>> orders = orderClient.getAllOrders(role);

        long buy = orders.stream()
                .filter(o -> "BUY".equals(o.get("orderType")))
                .count();

        long sell = orders.stream()
                .filter(o -> "SELL".equals(o.get("orderType")))
                .count();

        Map<String, Long> result = new HashMap<>();
        result.put("BUY", buy);
        result.put("SELL", sell);

        return result;
    }

    public String getTopCustomer(String role) {

        List<Map<String, Object>> orders = orderClient.getAllOrders(role);

        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> (String) o.get("customerId"),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }
}