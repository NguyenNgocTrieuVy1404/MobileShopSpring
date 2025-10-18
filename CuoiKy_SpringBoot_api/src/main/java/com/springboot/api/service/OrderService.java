package com.springboot.api.service;

import com.springboot.api.entity.Order;
import java.util.List;

public interface OrderService {
    
    List<Order> getAllOrders();
    
    Order getOrderById(Long id);
    
    List<Order> getOrdersByUserId(Long userId);
    
    List<Order> getOrdersByStatus(String status);
    
    Order saveOrder(Order order);
    
    void deleteOrder(Long id);
} 