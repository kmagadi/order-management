package com.karthik.orderservice.repository;

import com.karthik.orderservice.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    public void save(Order order) {
        orders.put(order.getId(), order);
    }

    public Order findById(String id) {
        return orders.get(id);
    }

    public Collection<Order> findAll() {
        return orders.values();
    }

}