package com.karthik.orderservice.controller;

import com.karthik.orderservice.dto.OrderRequestDTO;
import com.karthik.orderservice.dto.OrderResponseDTO;
import com.karthik.orderservice.exception.OrderException;
import com.karthik.orderservice.model.Order;
import com.karthik.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @RequestHeader("X-Role") String role,
            @Valid @RequestBody OrderRequestDTO request) {

        if (!role.equals("ADMIN") && !role.equals("TRADER")) {
            throw new RuntimeException("Access denied: insufficient permissions");
        }

        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(
            @RequestHeader("X-Role") String role) {

        if (!role.equals("ADMIN") && !role.equals("VIEWER")) {
            throw new OrderException("Access denied: insufficient permissions");
        }

        return ResponseEntity.ok(orderService.getAllOrders());
    }
}