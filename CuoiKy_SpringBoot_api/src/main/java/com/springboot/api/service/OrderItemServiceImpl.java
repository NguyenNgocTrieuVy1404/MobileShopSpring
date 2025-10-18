package com.springboot.api.service;

import com.springboot.api.dao.OrderItemDao;
import com.springboot.api.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemDao orderItemDao;

    @Autowired
    public OrderItemServiceImpl(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemDao.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemDao.findById(id);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemDao.findByOrderId(orderId);
    }

    @Override
    public List<OrderItem> getOrderItemsByProductId(Long productId) {
        return orderItemDao.findByProductId(productId);
    }

    @Override
    @Transactional
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemDao.save(orderItem);
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long id) {
        orderItemDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteOrderItemsByOrderId(Long orderId) {
        orderItemDao.deleteByOrderId(orderId);
    }
} 