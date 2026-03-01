package com.karthik.ordermanagement.controller;

import com.karthik.ordermanagement.model.Order;
import com.karthik.ordermanagement.service.AnalyticsService;
import com.karthik.ordermanagement.service.OrderService;
import com.karthik.ordermanagement.dto.OrderRequestDTO;
import com.karthik.ordermanagement.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AnalyticsService analyticsService;

    @GetMapping("/{id}")
    public Order get(@PathVariable String id) {
        return orderService.getOrder(id);
    }

    @GetMapping
    public List<Order> all() {
        return orderService.getAllOrders();
    }

    @GetMapping("/analytics/total")
    public double total() {
        return analyticsService.totalOrderAmount();
    }

    @GetMapping("/analytics/buy-sell")
    public Map<String, Long> buySell() {
        return analyticsService.buyVsSell();
    }

    @GetMapping("/analytics/top-customer")
    public String topCustomer() {
        return analyticsService.topCustomer();
    }

    @GetMapping("/analytics/status")
    public Map<?, ?> status() {
        return analyticsService.groupByStatus();
    }

    @PostMapping
    public OrderResponseDTO place(@Valid @RequestBody OrderRequestDTO request) {

        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .product(request.getProduct())
                .orderType(request.getOrderType())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        Order saved = orderService.placeOrder(order);

        return OrderResponseDTO.builder()
                .orderId(saved.getOrderId())
                .status(saved.getStatus())
                .message("Order accepted for processing")
                .build();
    }
}