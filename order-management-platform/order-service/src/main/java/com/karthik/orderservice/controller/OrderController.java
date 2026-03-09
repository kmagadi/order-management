package com.karthik.orderservice.controller;

import com.karthik.orderservice.dto.OrderRequestDTO;
import com.karthik.orderservice.dto.OrderResponseDTO;
import com.karthik.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @Valid @RequestBody OrderRequestDTO request) {

        log.info("Received order request for customer: {}", request.getCustomerId());

        OrderResponseDTO response = orderService.placeOrder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}