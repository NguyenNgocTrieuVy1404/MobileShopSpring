package com.springboot.api.service;

import com.springboot.api.entity.User;
import com.springboot.api.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
    
    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber);
    }
    
    @Override
    @Transactional
    public User saveUser(User user) {
        return userDao.save(user);
    }
    
    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        User existingUser = userDao.findById(id);
        if (existingUser == null) {
            return null;
        }
        
        user.setId(id);
        return userDao.save(user);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }
    
    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userDao.existsByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }
} 