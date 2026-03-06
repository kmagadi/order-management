package com.karthik.ordermanagement.validation;

import com.karthik.ordermanagement.model.Order;

public interface OrderValidator {

    void validate(Order order);
}