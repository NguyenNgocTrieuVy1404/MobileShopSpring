# Mobile Shop - Hệ Thống Bán Điện Thoại/Laptop

## Mô tả
Dự án học tập xây dựng hệ thống e-commerce bán điện thoại, laptop và phụ kiện bằng Spring Boot với kiến trúc microservices. Hiện tại đã setup Eureka Server và đang phát triển các service khác.

## Tình trạng dự án
- ✅ **Eureka Server**: Service Discovery đã hoàn thành
- ✅ **API Gateway**: Gateway đã cấu hình routing
- 🚧 **Main Service**: Spring Boot web application (CuoiKy_SpringBoot)
- 🚧 **API Service**: REST API endpoints (CuoiKy_SpringBoot_api)
- 📋 **Database**: Schema đã có sẵn (shopping.sql)

## Chức năng dự kiến
- 🔄 **Mua sắm trực tuyến**: Duyệt và mua sản phẩm điện tử
- 🔄 **Quản lý sản phẩm**: Admin quản lý điện thoại, laptop, phụ kiện
- 🔄 **Giỏ hàng**: Thêm/xóa sản phẩm, tính tổng tiền
- 🔄 **Đặt hàng**: Tạo đơn hàng với thông tin giao hàng
- 🔄 **Xác thực người dùng**: Đăng ký, đăng nhập với Spring Security
- 🔄 **Microservices**: Eureka Server, API Gateway, Service Discovery

## Kiến trúc Microservices

### Spring Boot Microservices Architecture
```
MobileShopSpring/
├── eureka-server/              # Service Discovery
│   ├── EurekaServerApplication.java
│   └── application.properties
├── api-gateway/                # API Gateway
│   ├── ApiGetwayApplication.java
│   └── application.properties
├── CuoiKy_SpringBoot/         # Main Web Application
│   ├── controllers/            # REST Controllers
│   ├── services/               # Business Logic
│   ├── entities/               # JPA Entities
│   ├── dao/                    # Data Access Objects
│   ├── security/               # Spring Security Config
│   └── templates/              # Thymeleaf Templates
├── CuoiKy_SpringBoot_api/     # API Service
│   └── REST API endpoints
└── shopping.sql               # Database Schema
```

## Công nghệ sử dụng
- **Spring Boot 3.4** - Main framework
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Data persistence
- **Spring Cloud** - Microservices
- **Eureka Server** - Service discovery
- **API Gateway** - Request routing
- **MySQL** - Database
- **Thymeleaf** - Template engine
- **Maven** - Build tool

## Cài đặt

### 1. Yêu cầu
- Java 23+
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA/Eclipse)

### 2. Setup database
```sql
-- Import file shopping.sql vào MySQL
mysql -u root -p < shopping.sql
```

### 3. Chạy các service đã setup

#### Bước 1: Chạy Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
Truy cập: http://localhost:8761

#### Bước 2: Chạy API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```
Truy cập: http://localhost:9191

### 4. Các service đang phát triển
- **Main Service** (CuoiKy_SpringBoot): Cần implement controllers, services, entities
- **API Service** (CuoiKy_SpringBoot_api): Cần tạo REST endpoints

## Hướng dẫn phát triển tiếp

### 1. API Gateway đã cấu hình sẵn
Gateway đã có routing:
- `/api/**` → CuoiKy_SpringBoot_api service
- `/admin/**` → CuoiKy_SpringBoot service

### 2. Phát triển Main Service (CuoiKy_SpringBoot)
Cần implement:
- Controllers cho web interface
- Services cho business logic  
- Entities cho database mapping
- Security configuration
- Thymeleaf templates

### 3. Phát triển API Service (CuoiKy_SpringBoot_api)
Cần tạo:
- REST Controllers
- DTOs cho data transfer
- Service layer
- Repository layer
- Eureka client registration

## Cấu trúc database

### Các bảng chính
- **users**: Tài khoản người dùng
- **authorities**: Phân quyền (ROLE_CUSTOMER, ROLE_ADMIN)
- **product**: Sản phẩm (điện thoại, laptop, phụ kiện)
- **orders**: Đơn hàng
- **order_items**: Chi tiết đơn hàng

### Dữ liệu mẫu
- **Sản phẩm**: iPhone 14 Pro Max, Samsung Galaxy S23, MacBook Pro M2, Dell XPS 15...
- **Người dùng**: customer, phongvy (admin), trieuvy
- **Đơn hàng**: Mẫu đơn hàng với trạng thái khác nhau

## Cách sử dụng

### 1. Khách hàng
- **Đăng ký/Đăng nhập**: Tạo tài khoản hoặc đăng nhập
- **Duyệt sản phẩm**: Xem danh sách điện thoại, laptop
- **Chi tiết sản phẩm**: Xem thông tin chi tiết và giá
- **Giỏ hàng**: Thêm sản phẩm vào giỏ hàng
- **Đặt hàng**: Tạo đơn hàng với thông tin giao hàng
- **Lịch sử đơn hàng**: Xem các đơn hàng đã đặt

### 2. Admin
- **Quản lý sản phẩm**: Thêm/sửa/xóa sản phẩm
- **Quản lý người dùng**: Quản lý tài khoản khách hàng
- **Dashboard**: Thống kê tổng quan hệ thống
- **Quản lý đơn hàng**: Xem và cập nhật trạng thái đơn hàng

## API Endpoints

### Authentication
- `POST /register` - Đăng ký tài khoản
- `POST /login` - Đăng nhập
- `POST /logout` - Đăng xuất

### Products
- `GET /products` - Lấy danh sách sản phẩm
- `GET /products/{id}` - Chi tiết sản phẩm
- `POST /products` - Tạo sản phẩm (Admin)
- `PUT /products/{id}` - Cập nhật sản phẩm (Admin)

### Orders
- `GET /orders` - Lấy danh sách đơn hàng
- `POST /orders` - Tạo đơn hàng mới
- `GET /orders/{id}` - Chi tiết đơn hàng

## Tính năng nổi bật

### 1. Microservices Architecture
- **Service Discovery**: Eureka Server quản lý các service
- **API Gateway**: Routing và load balancing
- **Independent Deployment**: Mỗi service có thể deploy riêng

### 2. Security
- **Spring Security**: Authentication và authorization
- **Role-based Access**: Phân quyền Customer/Admin
- **Password Encryption**: Bcrypt password hashing

### 3. Data Management
- **JPA/Hibernate**: ORM mapping
- **MySQL Integration**: Relational database
- **Transaction Management**: ACID compliance

### 4. Frontend
- **Thymeleaf**: Server-side rendering
- **Responsive Design**: Mobile-friendly interface
- **Bootstrap**: Modern UI components

### 5. E-commerce Features
- **Shopping Cart**: Session-based cart management
- **Order Processing**: Complete order workflow
- **Product Management**: CRUD operations
- **User Management**: Account management

## Lưu ý
- Đây là dự án học tập Spring Boot microservices đang phát triển
- Eureka Server và API Gateway đã setup hoàn chỉnh
- Main Service và API Service cần được implement tiếp
- Database schema đã sẵn sàng để sử dụng
- Phù hợp để học Spring Boot và microservices architecture
