package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.User;
import com.springboot.dev_spring_boot_demo.entity.Order;
import com.springboot.dev_spring_boot_demo.service.UserService;
import com.springboot.dev_spring_boot_demo.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserProfileController {

    private UserService userService;
    private OrderService orderService;

    @Autowired
    public UserProfileController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    // Hiển thị trang profile
    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        // Lấy thông tin người dùng hiện tại
        User user = getCurrentUser();
        
        model.addAttribute("user", user);
        // model.addAttribute("orders", orders);
        
        return "user/profile";
    }
    
    // Cập nhật thông tin cá nhân
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User user, 
                               RedirectAttributes redirectAttributes,
                               BindingResult bindingResult,
                               Model model) {
        
        // Lấy thông tin người dùng hiện tại để cập nhật
        User currentUser = getCurrentUser();
        
        // Gọi service để xử lý cập nhật thông tin
        boolean updateSuccess = userService.updateUserProfile(user, currentUser, bindingResult);
        
        if (!updateSuccess) {
            model.addAttribute("user", currentUser);
            return "user/profile";
        }
        
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành công");
        return "redirect:/user/profile";
    }
    
    // Đổi mật khẩu
    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                RedirectAttributes redirectAttributes,
                                BindingResult bindingResult,
                                Model model) {
        
        // Lấy thông tin người dùng hiện tại
        User currentUser = getCurrentUser();
        
        // Gọi service để xử lý thay đổi mật khẩu
        boolean changeSuccess = userService.changeUserPassword(
            currentPassword, newPassword, confirmPassword, currentUser, bindingResult);
        
        if (!changeSuccess) {
            model.addAttribute("user", currentUser);
            return "user/profile";
        }
        
        redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công");
        return "redirect:/user/profile";
    }
    
    // Xem chi tiết đơn hàng
    @GetMapping("/orders/detail")
    public String viewOrderDetail(@RequestParam("id") Long orderId, Model model) {
        // Lấy thông tin người dùng hiện tại
        User currentUser = getCurrentUser();
        
        // Lấy thông tin đơn hàng
        Order order = orderService.findById(orderId);
        
        // Kiểm tra đơn hàng có thuộc về người dùng không
        if (order == null || !order.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/user/profile";
        }
        
        model.addAttribute("order", order);
        
        return "user/order-detail";
    }
    
    // Hiển thị trang lịch sử đơn hàng
    @GetMapping("/orders")
    public String showOrderHistory(Model model) {
        // Lấy thông tin người dùng hiện tại
        User currentUser = getCurrentUser();
        
        // Lấy danh sách đơn hàng của người dùng
        List<Order> orders = orderService.findByUserId(currentUser.getId());
        
        model.addAttribute("orders", orders);
        model.addAttribute("user", currentUser);
        
        return "user/order-history";
    }
    
    // Phương thức tiện ích để lấy người dùng hiện tại
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username);
    }
} 