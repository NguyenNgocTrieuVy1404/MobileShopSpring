package com.springboot.api.service;

import com.springboot.api.entity.Product;
import java.util.List;

public interface ProductService {
    
    List<Product> getAllProducts();
    
    Product getProductById(Long id);
    
    List<Product> getProductsByCategory(String category);
    
    List<Product> searchProducts(String keyword);
    
    Product saveProduct(Product product);
    
    void deleteProduct(Long id);
} 