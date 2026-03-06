package com.karthik.ordermanagement.validation;

import com.karthik.ordermanagement.exception.OrderException;
import com.karthik.ordermanagement.model.Order;
import org.springframework.stereotype.Component;

@Component
public class PriceValidator implements OrderValidator {

    @Override
    public void validate(Order order) {
        if (order.getPrice() <= 0) {
            throw new OrderException("Price must be greater than zero");
        }
    }
}