package com.karthik.orderservice.exception;

public class OrderNotFoundException extends OrderException {

    public OrderNotFoundException(String id) {
        super("Order not found: " + id);
    }
}