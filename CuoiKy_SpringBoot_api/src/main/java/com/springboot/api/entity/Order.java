package com.springboot.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotNull(message = "Ngày đặt hàng không được để trống")
    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    
    @NotNull(message = "Tổng tiền không được để trống")
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    
    @NotEmpty(message = "Trạng thái không được để trống")
    @Column(name = "status")
    private String status;
    
    @NotEmpty(message = "Tên khách hàng không được để trống")
    @Column(name = "customer_name")
    private String customerName;
    
    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Column(name = "customer_email")
    private String customerEmail;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @NotEmpty(message = "Địa chỉ giao hàng không được để trống")
    @Column(name = "shipping_address")
    private String shippingAddress;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Order() {
    }
    
    public Order(Date orderDate, BigDecimal totalAmount, String status, String customerName,
                String customerEmail, String phoneNumber, String shippingAddress, User user) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
        this.user = user;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }
} 