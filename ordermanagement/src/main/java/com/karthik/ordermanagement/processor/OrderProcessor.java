package com.karthik.ordermanagement.processor;

import com.karthik.ordermanagement.model.*;
import com.karthik.ordermanagement.repository.OrderRepository;
import com.karthik.ordermanagement.util.FileLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
public class OrderProcessor {

    private final OrderRepository repository;
    private final FileLogger logger;

    private final ExecutorService executor;

    public void process(Order order) {

        executor.submit(() -> {
            try {
                order.setStatus(OrderStatus.PROCESSING);
                repository.save(order);

                // simulate execution delay
                Thread.sleep(1000);

                order.setStatus(OrderStatus.COMPLETED);
                repository.save(order);

                logger.log("Order completed: " + order.getOrderId());

            } catch (Exception e) {
                order.setStatus(OrderStatus.FAILED);
                repository.save(order);
                logger.log("Order failed: " + order.getOrderId());
            }
        });
    }
}