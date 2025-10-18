package com.springboot.dev_spring_boot_demo.dao;

import com.springboot.dev_spring_boot_demo.entity.Order;
import java.util.List;

public interface OrderDAO {
    List<Order> findByUserId(Long userId);
    Order findById(Long id);
    void save(Order order);
} 