package com.springboot.dev_spring_boot_demo.service;

import com.springboot.dev_spring_boot_demo.entity.CartItem;
import com.springboot.dev_spring_boot_demo.entity.Order;
import com.springboot.dev_spring_boot_demo.entity.User;
import java.util.List;

public interface OrderService {
    List<Order> findByUserId(Long userId);
    Order findById(Long id);
    void save(Order order);
    
    // Thêm phương thức mới để xử lý quy trình đặt hàng
    Order processOrder(Order order, List<CartItem> cartItems, User currentUser);
} 