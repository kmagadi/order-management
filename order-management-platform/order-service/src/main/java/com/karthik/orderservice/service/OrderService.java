package com.karthik.orderservice.service;

import com.karthik.orderservice.dto.OrderRequestDTO;
import com.karthik.orderservice.dto.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO placeOrder(OrderRequestDTO request);

}