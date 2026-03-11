package com.training_spring_cloud.analytics_service.service.impl;

import com.karthik.analyticsservice.client.OrderClient;
import com.karthik.analyticsservice.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderClient orderClient;

    @Override
    public Map<String, Object> getSummary() {

        List<Map<String, Object>> orders = orderClient.getAllOrders();

        int totalOrders = orders.size();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalOrders", totalOrders);

        return summary;
    }

    @Override
    public Map<String, Long> getBuyVsSell() {

        List<Map<String, Object>> orders = orderClient.getAllOrders();

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

    @Override
    public String getTopCustomer() {

        List<Map<String, Object>> orders = orderClient.getAllOrders();

        return orders.stream()
                .collect(
                        Collectors.groupingBy(
                                o -> (String) o.get("customerId"),
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }
}}