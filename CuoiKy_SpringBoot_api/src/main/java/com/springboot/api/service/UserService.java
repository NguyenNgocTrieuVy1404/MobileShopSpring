package com.springboot.api.service;

import com.springboot.api.entity.User;
import java.util.List;

public interface UserService {
    
    List<User> getAllUsers();
    
    User getUserById(Long id);
    
    User getUserByUsername(String username);
    
    User getUserByEmail(String email);
    
    User getUserByPhoneNumber(String phoneNumber);
    
    User saveUser(User user);
    
    User updateUser(Long id, User user);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhoneNumber(String phoneNumber);
    
    void deleteUser(Long id);
} 