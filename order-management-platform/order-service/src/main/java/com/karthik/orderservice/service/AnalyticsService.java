package com.karthik.orderservice.service;

import java.util.Map;

public interface AnalyticsService {

    double totalOrderAmount();

    Map<String, Long> buyVsSell();

    String topCustomer();

    Map<String, Long> groupByStatus();
}