package com.springboot.dev_spring_boot_demo.service;

import com.springboot.dev_spring_boot_demo.entity.User;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {
    void registerNewUser(User user);

    boolean isUsernameExists(String username);

    boolean isEmailExists(String email);
    
    boolean isPhoneNumberExists(String phoneNumber);

    User findByUsername(String username);
    void save(User user);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    
    // Các phương thức mới để làm việc với role
    String getUserRole(String username);
    void updateUserRole(String username, String role);

    // Các phương thức cho UserAdminController
    List<User> getAllUsers();
    void saveUser(User user, String newPassword, String role);
    User getUserById(int id);
    void updateUser(User user);
    void deleteUser(Long id);
    void toggleUserStatus(int id);

    // Thêm các phương thức mới
    boolean updateUserProfile(User updatedData, User currentUser, BindingResult errors);
    boolean changeUserPassword(String currentPassword, String newPassword, String confirmPassword, User currentUser, BindingResult errors);
    boolean validateUserRegistration(User user, BindingResult bindingResult);
}