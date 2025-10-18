package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.Product;
import com.springboot.dev_spring_boot_demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {
    private ProductService productService;

    @Autowired
    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products/list-products";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "admin/products/add-product";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("productId") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/products/update-product";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return product.getId() == null ? "admin/products/add-product" : "admin/products/update-product";
        }

        productService.save(product);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("productId") Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products/list";
    }
}