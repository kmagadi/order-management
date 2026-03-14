package com.training_spring_cloud.analytics_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {

    @GetMapping("/orders")
    List<Map<String, Object>> getAllOrders(
            @RequestHeader("X-Role") String role
    );
}