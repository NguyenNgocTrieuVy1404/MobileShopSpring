package com.springboot.dev_spring_boot_demo.dao;

import com.springboot.dev_spring_boot_demo.entity.User;
import java.util.List;

public interface UserDAO {
    void save(User user);

    User findByUsername(String username);

    User findByEmail(String email);
    
    User findByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    
    boolean existsByPhoneNumber(String phoneNumber);

    User findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

    void deleteAuthoritiesByUsername(String username);
    void deleteOrdersByUserId(Long userId);
}