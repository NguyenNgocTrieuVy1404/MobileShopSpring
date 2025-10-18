package com.springboot.dev_spring_boot_demo.service;

import com.springboot.dev_spring_boot_demo.dao.UserDAO;
import com.springboot.dev_spring_boot_demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void registerNewUser(User user) {
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Không cần thiết lập role cho User nữa
        user.setEnabled(true);

        // Lưu user
        userDAO.save(user);

        // Thêm quyền vào bảng authorities
        jdbcTemplate.update(
                "INSERT INTO authorities (username, authority) VALUES (?, ?)",
                user.getUsername(), "ROLE_CUSTOMER");
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userDAO.existsByUsername(username);
    }

    @Override
    public boolean isEmailExists(String email) {
        return userDAO.existsByEmail(email);
    }
    
    @Override
    public boolean isPhoneNumberExists(String phoneNumber) {
        return userDAO.existsByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @Transactional
    public void save(User user) {
        // Kiểm tra xem user đã tồn tại chưa
        User existingUser = null;
        if (user.getId() != null) {
            existingUser = userDAO.findById(user.getId());
        }
        
        // Nếu đây là một user đã tồn tại và mật khẩu đã thay đổi
        if (existingUser != null) {
            // Nếu mật khẩu đã được mã hóa (có thể nhận biết qua độ dài), không cần mã hóa lại
            if (user.getPassword().length() < 20) {
                // Mật khẩu chưa được mã hóa, cần mã hóa trước khi lưu
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        
        userDAO.save(user);
    }
    
    @Override
    @Transactional
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
    
    @Override
    @Transactional
    public User findByPhoneNumber(String phoneNumber) {
        return userDAO.findByPhoneNumber(phoneNumber);
    }

    @Override
    public String getUserRole(String username) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT authority FROM authorities WHERE username = ?", 
                String.class, username);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public void updateUserRole(String username, String role) {
        // Kiểm tra xem người dùng đã có role chưa
        String currentRole = getUserRole(username);
        
        if (currentRole == null) {
            // Nếu chưa có role, thêm mới
            jdbcTemplate.update(
                "INSERT INTO authorities (username, authority) VALUES (?, ?)",
                username, role);
        } else {
            // Nếu đã có role, cập nhật
            jdbcTemplate.update(
                "UPDATE authorities SET authority = ? WHERE username = ?",
                role, username);
        }
    }

    // Các phương thức cho UserAdminController
    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user, String newPassword, String role) {
        // Nếu là user mới
        if (user.getId() == null) {
            // Mã hóa mật khẩu cho user mới
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            // Lưu user
            userDAO.save(user);
            // Thêm role mặc định cho user mới
            updateUserRole(user.getUsername(), role != null ? role : "ROLE_CUSTOMER");
        } else {
            // Nếu là cập nhật user
            User existingUser = userDAO.findById(user.getId());
            if (existingUser != null) {
                // Chỉ cập nhật mật khẩu nếu có mật khẩu mới
                if (newPassword != null && !newPassword.isEmpty()) {
                    user.setPassword(passwordEncoder.encode(newPassword));
                } else {
                    user.setPassword(existingUser.getPassword());
                }
                
                // Cập nhật thông tin user
                userDAO.save(user);
                
                // Cập nhật role nếu có
                if (role != null && !role.isEmpty()) {
                    updateUserRole(user.getUsername(), role);
                }
            }
        }
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return userDAO.findById((long) id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        // Nếu mật khẩu mới được cập nhật
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDAO.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
                
        // Xóa các bản ghi trong bảng authorities
        userDAO.deleteAuthoritiesByUsername(user.getUsername());
        
        // Xóa các đơn hàng của user
        userDAO.deleteOrdersByUserId(id);
        
        // Xóa user
        userDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void toggleUserStatus(int id) {
        User user = userDAO.findById((long) id);
        if (user != null) {
            user.setEnabled(!user.isEnabled());
            userDAO.save(user);
        }
    }

    @Override
    @Transactional
    public boolean updateUserProfile(User updatedData, User currentUser, BindingResult errors) {
        // Cập nhật các trường thông tin cá nhân
        currentUser.setFullName(updatedData.getFullName());
        
        // Kiểm tra email
        if (!currentUser.getEmail().equals(updatedData.getEmail())) {
            User existingUser = findByEmail(updatedData.getEmail());
            if (existingUser != null && !existingUser.getId().equals(currentUser.getId())) {
                errors.rejectValue("email", "error.user", "Email đã được sử dụng bởi tài khoản khác");
                return false;
            }
            currentUser.setEmail(updatedData.getEmail());
        }
        
        // Kiểm tra số điện thoại
        if (updatedData.getPhoneNumber() != null && !updatedData.getPhoneNumber().isEmpty()) {
            if (!updatedData.getPhoneNumber().matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                errors.rejectValue("phoneNumber", "error.user", "Số điện thoại không hợp lệ");
                return false;
            }
            currentUser.setPhoneNumber(updatedData.getPhoneNumber());
        }
        
        save(currentUser);
        return true;
    }

    @Override
    @Transactional
    public boolean changeUserPassword(String currentPassword, String newPassword, 
                                 String confirmPassword, User currentUser, BindingResult errors) {
        // Kiểm tra xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            errors.rejectValue("password", "error.password", "Mật khẩu xác nhận không khớp");
            return false;
        }
        
        // Kiểm tra độ dài của mật khẩu
        if (newPassword.length() < 6) {
            errors.rejectValue("password", "error.password", "Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }
        
        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            errors.rejectValue("password", "error.password", "Mật khẩu hiện tại không đúng");
            return false;
        }
        
        // Cập nhật mật khẩu mới
        String encodedPassword = passwordEncoder.encode(newPassword);
        currentUser.setPassword(encodedPassword);
        save(currentUser);
        
        return true;
    }

    @Override
    @Transactional
    public boolean validateUserRegistration(User user, BindingResult bindingResult) {
        boolean isValid = true;
        
        // Kiểm tra username đã tồn tại chưa
        if (isUsernameExists(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Tên đăng nhập đã được sử dụng");
            isValid = false;
        }

        // Kiểm tra email đã tồn tại chưa
        if (isEmailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email đã được sử dụng");
            isValid = false;
        }
        
        // Kiểm tra số điện thoại đã tồn tại chưa
        if (isPhoneNumberExists(user.getPhoneNumber())) {
            bindingResult.rejectValue("phoneNumber", "error.user", "Số điện thoại đã được sử dụng");
            isValid = false;
        }
        
        return isValid;
    }
}