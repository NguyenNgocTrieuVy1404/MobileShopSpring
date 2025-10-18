package com.springboot.api.dao;

import com.springboot.api.entity.Order;
import java.util.List;

public interface OrderDao {
    
    List<Order> findAll();
    
    Order findById(Long id);
    
    List<Order> findByUserId(Long userId);
    
    List<Order> findByStatus(String status);
    
    Order save(Order order);
    
    void deleteById(Long id);
} 