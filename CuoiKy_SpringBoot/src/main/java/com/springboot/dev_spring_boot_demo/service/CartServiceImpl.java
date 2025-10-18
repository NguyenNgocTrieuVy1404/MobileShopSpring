package com.springboot.dev_spring_boot_demo.service;

import com.springboot.dev_spring_boot_demo.entity.CartItem;
import com.springboot.dev_spring_boot_demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {
    private List<CartItem> cartItems = new ArrayList<>();

    @Autowired
    private ProductService productService;

    @Override
    public void addToCart(Product product, int quantity) {
        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Nếu chưa có, thêm mới
        cartItems.add(new CartItem(product, quantity));
    }

    @Override
    public void updateQuantity(Long productId, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    @Override
    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    @Override
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public double getTotalAmount() {
        return cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    @Override
    public void clear() {
        cartItems.clear();
    }

    @Override
    public boolean addToCartWithStockCheck(Long productId, int quantity) {
        Product product = productService.findById(productId);
        if (product == null || product.getStockQuantity() <= 0) {
            return false;
        }
        
        // Kiểm tra số lượng đặt hàng không vượt quá tồn kho
        int currentQuantityInCart = 0;
        for (CartItem item : getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                currentQuantityInCart = item.getQuantity();
                break;
            }
        }
        
        int totalQuantity = currentQuantityInCart + quantity;
        if (totalQuantity <= product.getStockQuantity()) {
            addToCart(product, quantity);
            return true;
        }
        
        return false;
    }

    @Override
    public boolean updateQuantityWithStockCheck(Long productId, int quantity) {
        // Kiểm tra tham số đầu vào
        if (quantity <= 0) {
            return false;
        }
        
        // Kiểm tra tồn kho sản phẩm
        Product product = productService.findById(productId);
        if (product == null || product.getStockQuantity() < quantity) {
            return false;
        }
        
        // Nếu đủ tồn kho, cập nhật số lượng
        updateQuantity(productId, quantity);
        return true;
    }
}