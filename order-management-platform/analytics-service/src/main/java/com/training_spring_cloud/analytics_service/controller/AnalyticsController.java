package com.training_spring_cloud.analytics_service.controller;

import com.training_spring_cloud.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
    public Map<String, Object> summary(
            @RequestHeader("X-Role") String role) {

        if (!role.equals("ADMIN") && !role.equals("VIEWER")) {
            throw new RuntimeException("Access denied");
        }

        return analyticsService.getSummary();
    }
}