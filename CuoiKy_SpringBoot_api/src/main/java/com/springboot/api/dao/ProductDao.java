package com.springboot.api.dao;

import com.springboot.api.entity.Product;
import java.util.List;

public interface ProductDao {
    
    List<Product> findAll();
    
    Product findById(Long id);
    
    List<Product> findByCategory(String category);
    
    List<Product> findByNameContainingOrDescriptionContainingOrCategoryContaining(
            String name, String description, String category);
    
    Product save(Product product);
    
    void deleteById(Long id);
} 