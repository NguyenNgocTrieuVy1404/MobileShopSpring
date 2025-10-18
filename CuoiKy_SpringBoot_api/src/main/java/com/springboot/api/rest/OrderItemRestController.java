package com.springboot.api.rest;

import com.springboot.api.entity.OrderItem;
import com.springboot.api.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemRestController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemRestController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("")
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        if (orderItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderItem);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItem> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    @GetMapping("/product/{productId}")
    public List<OrderItem> getOrderItemsByProductId(@PathVariable Long productId) {
        return orderItemService.getOrderItemsByProductId(productId);
    }

    @PostMapping("")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        orderItem.setId(null); // Đảm bảo tạo mới
        OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        OrderItem existingOrderItem = orderItemService.getOrderItemById(id);
        
        if (existingOrderItem == null) {
            return ResponseEntity.notFound().build();
        }
        
        orderItem.setId(id);
        OrderItem updatedOrderItem = orderItemService.saveOrderItem(orderItem);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long id) {
        OrderItem existingOrderItem = orderItemService.getOrderItemById(id);
        
        if (existingOrderItem == null) {
            return ResponseEntity.notFound().build();
        }
        
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok("Xóa mục đơn hàng thành công với id = " + id);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
        
        if (orderItems.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        orderItemService.deleteOrderItemsByOrderId(orderId);
        return ResponseEntity.ok("Xóa tất cả mục đơn hàng của đơn hàng id = " + orderId);
    }
} 