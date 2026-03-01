package com.karthik.ordermanagement.service;

import com.karthik.ordermanagement.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Order order);

    Order getOrder(String id);

    List<Order> getAllOrders();
}