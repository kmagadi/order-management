package com.training_spring_cloud.analytics_service.service;

import java.util.Map;

public interface AnalyticsService {

    Map<String, Object> getSummary(String role);

    Map<String, Long> getBuyVsSell(String role);

    String getTopCustomer(String role);
}