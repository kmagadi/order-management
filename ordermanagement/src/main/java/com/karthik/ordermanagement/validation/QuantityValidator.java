package com.karthik.ordermanagement.validation;

import com.karthik.ordermanagement.exception.OrderException;
import com.karthik.ordermanagement.model.Order;
import org.springframework.stereotype.Component;

@Component
public class QuantityValidator implements OrderValidator {

    @Override
    public void validate(Order order) {
        if (order.getQuantity() <= 0) {
            throw new OrderException("Quantity must be greater than zero");
        }
    }
}