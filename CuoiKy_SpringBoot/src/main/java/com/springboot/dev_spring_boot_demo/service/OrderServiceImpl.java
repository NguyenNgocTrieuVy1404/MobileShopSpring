package com.springboot.dev_spring_boot_demo.service;

import com.springboot.dev_spring_boot_demo.dao.OrderDAO;
import com.springboot.dev_spring_boot_demo.entity.Order;
import com.springboot.dev_spring_boot_demo.entity.CartItem;
import com.springboot.dev_spring_boot_demo.entity.OrderItem;
import com.springboot.dev_spring_boot_demo.entity.Product;
import com.springboot.dev_spring_boot_demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;
    private ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, ProductService productService) {
        this.orderDAO = orderDAO;
        this.productService = productService;
    }

    @Override
    @Transactional
    public List<Order> findByUserId(Long userId) {
        return orderDAO.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order findById(Long id) {
        return orderDAO.findById(id);
    }

    @Override
    @Transactional
    public void save(Order order) {
        orderDAO.save(order);
    }
    
    @Override
    @Transactional
    public Order processOrder(Order order, List<CartItem> cartItems, User currentUser) {
        // Thiết lập ngày đặt hàng và trạng thái
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        
        // Gán người dùng nếu đã đăng nhập
        if (currentUser != null) {
            order.setUser(currentUser);
        }
        
        // Thêm các sản phẩm vào đơn hàng
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            OrderItem orderItem = new OrderItem(product, item.getQuantity(), product.getPrice());
            order.addOrderItem(orderItem);
            
            // Cập nhật lại số lượng tồn kho
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productService.save(product);
        }
        
        // Tính tổng tiền
        order.calculateTotalAmount();
        
        // Lưu đơn hàng
        save(order);
        
        return order;
    }
} 