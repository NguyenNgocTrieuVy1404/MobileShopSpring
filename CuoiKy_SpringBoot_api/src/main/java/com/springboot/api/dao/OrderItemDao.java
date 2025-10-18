package com.springboot.api.dao;

import com.springboot.api.entity.OrderItem;
import java.util.List;

public interface OrderItemDao {
    
    List<OrderItem> findAll();
    
    OrderItem findById(Long id);
    
    List<OrderItem> findByOrderId(Long orderId);
    
    List<OrderItem> findByProductId(Long productId);
    
    OrderItem save(OrderItem orderItem);
    
    void deleteById(Long id);
    
    void deleteByOrderId(Long orderId);
} 