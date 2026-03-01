package com.karthik.ordermanagement.exception;

public class OrderNotFoundException extends OrderException {

    public OrderNotFoundException(String id) {
        super("Order not found: " + id);
    }
}