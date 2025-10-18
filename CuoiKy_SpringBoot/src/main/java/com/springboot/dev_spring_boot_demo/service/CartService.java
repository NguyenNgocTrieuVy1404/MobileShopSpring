package com.springboot.dev_spring_boot_demo.service;

import com.springboot.dev_spring_boot_demo.entity.CartItem;
import com.springboot.dev_spring_boot_demo.entity.Product;

import java.util.List;

public interface CartService {
    void addToCart(Product product, int quantity);
    void updateQuantity(Long productId, int quantity);
    void removeFromCart(Long productId);
    List<CartItem> getCartItems();
    double getTotalAmount();
    void clear();
    
    // Phương thức mới thêm vào giỏ hàng có kiểm tra tồn kho
    boolean addToCartWithStockCheck(Long productId, int quantity);
    
    // Phương thức mới cập nhật số lượng có kiểm tra tồn kho
    boolean updateQuantityWithStockCheck(Long productId, int quantity);
}