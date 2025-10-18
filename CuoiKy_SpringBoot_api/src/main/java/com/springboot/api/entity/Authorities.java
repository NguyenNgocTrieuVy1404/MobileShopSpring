package com.springboot.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "authorities")
@IdClass(AuthoritiesId.class)
public class Authorities {

    @Id
    @NotEmpty(message = "Tên đăng nhập không được để trống")
    @Column(name = "username")
    private String username;
    
    @Id
    @NotEmpty(message = "Quyền không được để trống")
    @Column(name = "authority")
    private String authority;
    
    public Authorities() {
    }
    
    public Authorities(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getAuthority() {
        return authority;
    }
    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    @Override
    public String toString() {
        return "Authorities{" +
                "username='" + username + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
} 