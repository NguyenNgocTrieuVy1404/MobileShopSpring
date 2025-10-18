package com.springboot.api.service;

import com.springboot.api.dao.OrderDao;
import com.springboot.api.dao.OrderItemDao;
import com.springboot.api.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderItemDao orderItemDao) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderDao.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderDao.findByStatus(status);
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        return orderDao.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        // Xóa tất cả các mục đơn hàng trước
        orderItemDao.deleteByOrderId(id);
        // Sau đó xóa đơn hàng
        orderDao.deleteById(id);
    }
} 