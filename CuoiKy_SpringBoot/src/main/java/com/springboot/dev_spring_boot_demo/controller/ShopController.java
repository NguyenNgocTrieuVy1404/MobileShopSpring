package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.Product;
import com.springboot.dev_spring_boot_demo.entity.CartItem;
import com.springboot.dev_spring_boot_demo.entity.Order;
import com.springboot.dev_spring_boot_demo.entity.User;
import com.springboot.dev_spring_boot_demo.service.ProductService;
import com.springboot.dev_spring_boot_demo.service.CartService;
import com.springboot.dev_spring_boot_demo.service.OrderService;
import com.springboot.dev_spring_boot_demo.service.UserService;
import jakarta.validation.Valid;
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
public class ShopController {
    private ProductService productService;
    private CartService cartService;
    private OrderService orderService;
    private UserService userService;

    @Autowired
    public ShopController(ProductService productService, CartService cartService, 
                         OrderService orderService, UserService userService) {
        this.productService = productService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    // Trang chủ mặc định khi vào website
    @GetMapping("/")
    public String root() {
        return "redirect:/shop/main";
    }

    // Trang shop chính
    @GetMapping("/shop")
    public String shop() {
        return "redirect:/shop/main";
    }

    // Trang dashboard sau khi đăng nhập
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    // Trang main
    @GetMapping("/shop/main")
    public String main() {
        return "shop/main";
    }
    
    @GetMapping("/shop/about")
    public String about() {
        return "shop/about";
    }

    @GetMapping("/shop/service")
    public String service() {
        return "shop/service";
    }

    @GetMapping("/shop/contact")
    public String contact() {
        return "shop/contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/admin"; 
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    // Phương thức từ ProductController
    @GetMapping("/shop/products")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "shop/list-products";
    }

    @GetMapping("/shop/products/category/{category}")
    public String listProductsByCategory(@PathVariable String category, Model model) {
        List<Product> products = productService.findByCategory(category);
        model.addAttribute("products", products);
        model.addAttribute("currentCategory", category);
        return "shop/list-products";
    }

    @GetMapping("/shop/products/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "shop/list-products";
    }

    @GetMapping("/shop/products/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        
        Integer cartQuantity = null;
        for (CartItem item : cartService.getCartItems()) {
            if (item.getProduct().getId().equals(id)) {
                cartQuantity = item.getQuantity();
                break;
            }
        }
        
        model.addAttribute("product", product);
        model.addAttribute("cartQuantity", cartQuantity);
        return "shop/product-detail";
    }

    @GetMapping("/shop/cart")
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        return "shop/cart";
    }

    @PostMapping("/shop/cart/add")
    public String addToCart(@RequestParam("productId") Long productId,
            @RequestParam(value = "quantity", defaultValue = "1") int quantity) {
        cartService.addToCartWithStockCheck(productId, quantity);
        return "redirect:/shop/cart";
    }

    @PostMapping("/shop/cart/update")
    public String updateCart(@RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity,
            RedirectAttributes redirectAttributes) {
        
        boolean success = cartService.updateQuantityWithStockCheck(productId, quantity);
        
        if (!success) {
            // Nếu cập nhật không thành công (số lượng không hợp lệ hoặc vượt quá tồn kho)
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Không thể cập nhật số lượng sản phẩm. Vui lòng kiểm tra lại số lượng.");
        }
        
        return "redirect:/shop/cart";
    }

    @PostMapping("/shop/cart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/shop/cart";
    }
    
    @GetMapping("/shop/checkout")
    public String checkout(Model model) {
        // Kiểm tra giỏ hàng có sản phẩm không
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/shop/cart";
        }
        
        // Lấy thông tin user hiện tại (nếu đã đăng nhập)
        User currentUser = getCurrentUser();
        
        // Tạo đối tượng Order mới để binding form
        Order order = new Order();
        
        // Pre-fill thông tin từ user nếu đã đăng nhập
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
            order.setCustomerName(currentUser.getFullName());
            order.setCustomerEmail(currentUser.getEmail());
            order.setPhoneNumber(currentUser.getPhoneNumber());
        }
        
        model.addAttribute("order", order);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        
        return "shop/checkout";
    }
    
    @PostMapping("/shop/checkout/process")
    public String processCheckout(@Valid Order order, BindingResult bindingResult, 
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        // Kiểm tra có lỗi validation không
        if (bindingResult.hasErrors()) {
            // Lấy danh sách sản phẩm trong giỏ hàng để hiển thị lại
            List<CartItem> cartItems = cartService.getCartItems();
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalAmount", cartService.getTotalAmount());
            return "shop/checkout";
        }
        
        // Kiểm tra giỏ hàng có sản phẩm không
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/shop/cart";
        }
        
        // Kiểm tra tồn kho một lần nữa trước khi tạo đơn hàng
        boolean stockValid = true;
        StringBuilder errorMessage = new StringBuilder("Một số sản phẩm không đủ số lượng tồn kho: ");
        
        for (CartItem item : cartItems) {
            Product product = productService.findById(item.getProduct().getId());
            if (product == null || product.getStockQuantity() < item.getQuantity()) {
                stockValid = false;
                errorMessage.append(product != null ? product.getName() : "Sản phẩm không tồn tại")
                            .append(" (yêu cầu: ").append(item.getQuantity())
                            .append(", tồn kho: ").append(product != null ? product.getStockQuantity() : 0)
                            .append("), ");
            }
        }
        
        if (!stockValid) {
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage.toString());
            return "redirect:/shop/cart";
        }
        
        // Xử lý đơn hàng thông qua service layer
        User currentUser = getCurrentUser();
        Order savedOrder = orderService.processOrder(order, cartItems, currentUser);
        
        // Xóa giỏ hàng
        cartService.clear();
        
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());
        String orderCode = "DH-" + year + month + "-" + String.format("%03d", savedOrder.getId());
        
        redirectAttributes.addFlashAttribute("orderId", savedOrder.getId());
        redirectAttributes.addFlashAttribute("orderCode", orderCode);
        return "redirect:/shop/checkout/success";
    }
    
    @GetMapping("/shop/checkout/success")
    public String checkoutSuccess(Model model) {
        return "shop/checkout-success";
    }
    
    // Phương thức tiện ích để lấy người dùng hiện tại
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        String username = authentication.getName();
        return userService.findByUsername(username);
    }
}
