package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.User;
import com.springboot.dev_spring_boot_demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserAdminController {
    private UserService userService;

    @Autowired
    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users/list-users";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/users/add-user";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("userId") Long id, Model model) {
        User user = userService.getUserById(id.intValue());
        model.addAttribute("user", user);
        
        // Lấy role của user
        String userRole = userService.getUserRole(user.getUsername());
        model.addAttribute("userRole", userRole);
        
        return "admin/users/update-user";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") User user,
                          BindingResult bindingResult,
                          @RequestParam(required = false) String newPassword,
                          @RequestParam(required = false) String role,
                          Model model) {
        if (bindingResult.hasErrors()) {
            // Nếu là cập nhật user
            if (user.getId() != null) {
                String userRole = userService.getUserRole(user.getUsername());
                model.addAttribute("userRole", userRole);
                return "admin/users/update-user";
            }
            return "admin/users/add-user";
        }

        try {
            // Lưu user với mật khẩu mới và role
            userService.saveUser(user, newPassword, role);
            return "redirect:/admin/users/list";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi lưu thông tin người dùng: " + e.getMessage());
            if (user.getId() != null) {
                String userRole = userService.getUserRole(user.getUsername());
                model.addAttribute("userRole", userRole);
                return "admin/users/update-user";
            }
            return "admin/users/add-user";
        }
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/list";
    }

    @GetMapping("/toggleStatus")
    public String toggleUserStatus(@RequestParam("userId") int id) {
        userService.toggleUserStatus(id);
        return "redirect:/admin/users/list";
    }
} 