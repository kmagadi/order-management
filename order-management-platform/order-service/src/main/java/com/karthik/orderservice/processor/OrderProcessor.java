package com.karthik.orderservice.processor;

import com.karthik.orderservice.model.Order;
import com.karthik.orderservice.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderProcessor {

    @Async
    public void process(Order order) {

        try {

            order.setStatus(OrderStatus.PROCESSING);

            Thread.sleep(1000);

            order.setStatus(OrderStatus.COMPLETED);

            log.info("Order processed: {}", order.getId());

        } catch (Exception e) {

            order.setStatus(OrderStatus.FAILED);

            log.error("Order failed: {}", order.getId());

        }

    }
}