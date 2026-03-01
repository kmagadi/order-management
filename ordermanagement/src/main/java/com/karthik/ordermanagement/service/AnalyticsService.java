package com.karthik.ordermanagement.service;

import java.util.Map;

public interface AnalyticsService {

    double totalOrderAmount();

    Map<String, Long> buyVsSell();

    String topCustomer();

    Map<?, ?> groupByStatus();
}