package com.karthik.ordermanagement.dto;

import com.karthik.ordermanagement.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseDTO {

    private String orderId;
    private OrderStatus status;
    private String message;
}