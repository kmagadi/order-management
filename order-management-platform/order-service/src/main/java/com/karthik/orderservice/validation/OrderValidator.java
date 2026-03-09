package com.karthik.orderservice.validation;

import com.karthik.orderservice.model.Order;

public interface OrderValidator {

    void validate(Order order);
}