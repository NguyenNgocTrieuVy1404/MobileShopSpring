package com.springboot.api.dao;

import com.springboot.api.entity.User;
import java.util.List;

public interface UserDao {
    
    List<User> findAll();
    
    User findById(Long id);
    
    User findByUsername(String username);
    
    User findByEmail(String email);
    
    User findByPhoneNumber(String phoneNumber);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhoneNumber(String phoneNumber);
    
    User save(User user);
    
    void deleteById(Long id);
} 