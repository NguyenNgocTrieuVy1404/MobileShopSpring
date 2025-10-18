package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.User;
import com.springboot.dev_spring_boot_demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Sử dụng service để kiểm tra tính hợp lệ của dữ liệu đăng ký
        userService.validateUserRegistration(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        try {
            userService.registerNewUser(user);
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/register?success";
        } catch (Exception e) {
            bindingResult.rejectValue("username", "error.user", "Có lỗi xảy ra, vui lòng thử lại");
            model.addAttribute("user", user);
            return "register";
        }
    }
}