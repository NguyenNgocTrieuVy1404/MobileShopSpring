package com.springboot.api.service;

import com.springboot.api.entity.OrderItem;
import java.util.List;

public interface OrderItemService {
    
    List<OrderItem> getAllOrderItems();
    
    OrderItem getOrderItemById(Long id);
    
    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    
    List<OrderItem> getOrderItemsByProductId(Long productId);
    
    OrderItem saveOrderItem(OrderItem orderItem);
    
    void deleteOrderItem(Long id);
    
    void deleteOrderItemsByOrderId(Long orderId);
} 