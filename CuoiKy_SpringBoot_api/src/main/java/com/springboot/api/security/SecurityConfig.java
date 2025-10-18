package com.springboot.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Sử dụng DelegatingPasswordEncoder để hỗ trợ mật khẩu có tiền tố {bcrypt}
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        
        // Tùy chỉnh truy vấn cho cấu trúc database của bạn
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select username, password, enabled from users where username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select username, authority from authorities where username=?");
                
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring SecurityFilterChain");
        
        http.authorizeHttpRequests(configurer -> configurer
                // API quản lý user
                .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                
                // API quản lý product
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                
                // API quản lý authorities
                .requestMatchers(HttpMethod.GET, "/api/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/authorities/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/authorities/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/authorities/**").hasRole("ADMIN")
                
                // API quản lý orders
                .requestMatchers(HttpMethod.GET, "/api/orders").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/orders/{id}").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/orders/user/{userId}").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/orders/status/{status}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/orders").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/orders/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/orders/{id}").hasRole("ADMIN")
                
                // API quản lý order items
                .requestMatchers(HttpMethod.GET, "/api/order-items").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/order-items/{id}").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/order-items/order/{orderId}").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/order-items/product/{productId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/order-items").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/order-items/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/order-items/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
        );
        
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        
        http.csrf(csrf -> csrf.disable());
        
        return http.build();
    }
} 