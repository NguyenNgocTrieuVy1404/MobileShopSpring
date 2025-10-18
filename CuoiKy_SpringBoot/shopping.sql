-- Tạo database nếu chưa tồn tại
CREATE DATABASE IF NOT EXISTS shopping;
USE shopping;

-- Xóa các bảng cũ nếu cần tạo mới hoàn toàn (bỏ comment nếu muốn xóa)
-- DROP TABLE IF EXISTS order_items;
-- DROP TABLE IF EXISTS orders;
-- DROP TABLE IF EXISTS authorities;
-- DROP TABLE IF EXISTS product;
-- DROP TABLE IF EXISTS users;

-- Tạo bảng users với trường phone_number
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone_number` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tạo bảng authorities
CREATE TABLE `authorities` (
  `username` varchar(255) NOT NULL,
  `authority` varchar(50) NOT NULL,
  PRIMARY KEY (`username`,`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tạo bảng product
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `price` decimal(38,2) NOT NULL,
  `stock_quantity` int DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tạo bảng orders với trường phone_number
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_date` datetime DEFAULT NULL,
  `total_amount` decimal(38,2) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `customer_email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `shipping_address` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orders_ibfk_1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tạo bảng order_items
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_items_ibfk_1` (`order_id`),
  KEY `order_items_ibfk_2` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Thêm khóa ngoại
ALTER TABLE `authorities`
  ADD CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`);

ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

-- Thêm dữ liệu vào bảng users với trường phone_number
INSERT INTO `users` (`id`, `username`, `password`, `email`, `full_name`, `phone_number`, `enabled`) VALUES
(1, 'customer', '{bcrypt}$2a$10$HFh8aziIFHjPF4gz8g2RjOkKzXBfBKpIFovAhplzvRwWMrUN0x7v6', 'customer@gmail.com', 'Customer Đầu tiên', '0912345678', 1),
(2, 'phongvy', '{bcrypt}$2a$10$mHmeD3HHUQkN6w/alltLROAFXFj9Yk0WMzPSmcHBE.rV.nAZPronu', 'phongvy@gmail.com', 'PhongVỹ', '0923456789', 1),
(3, 'trieuvy', '{bcrypt}$2a$10$jgJIlMiPBc8dP2V9hYlNPOPVO5kP44GSvVWAVvKA9N006JoG61Eoi', 'phamthid@gmail.com', 'Vỹ Nguyễn', '0934567890', 1);

-- Thêm dữ liệu vào bảng authorities
INSERT INTO `authorities` (`username`, `authority`) VALUES
('customer', 'ROLE_CUSTOMER'),
('phongvy', 'ROLE_ADMIN'),
('trieuvy', 'ROLE_CUSTOMER');

-- Thêm dữ liệu vào bảng product
INSERT INTO `product` (`id`, `name`, `description`, `price`, `stock_quantity`, `image_url`, `category`) VALUES
(1, 'iPhone 14 Pro Max MINI', 'iPhone 14 Pro Max 256GB, màu đen, chip A16 Bionic', 29990000.00, 50, '/images/products/iphone14.jpg', 'PHONE'),
(2, 'Samsung Galaxy S23', 'Samsung Galaxy S23 Ultra 5G 256GB', 26990000.00, 45, '/images/products/samsung-s23.jpg', 'PHONE'),
(3, 'Xiaomi 13 Pro', 'Xiaomi 13 Pro 5G 256GB', 22990000.00, 30, '/images/products/xiaomi-13.jpg', 'PHONE'),
(4, 'MacBook Pro M2', 'MacBook Pro 14 inch M2 Pro 16GB RAM 512GB SSD', 52990000.00, 20, '/images/products/macbook-m2.jpg', 'LAPTOP'),
(5, 'Dell XPS 15', 'Dell XPS 15 9520 i7 12700H 16GB 512GB RTX 3050Ti', 49990000.00, 15, '/images/products/dell-xps.jpg', 'LAPTOP'),
(6, 'Lenovo ThinkPad', 'ThinkPad X1 Carbon Gen 10 i7 1260P 16GB 512GB', 42990000.00, 25, '/images/products/thinkpad.jpg', 'LAPTOP'),
(7, 'iPad Pro 2022', 'iPad Pro M2 11 inch WiFi 128GB', 20990000.00, 40, '/images/products/ipad-pro.jpg', 'TABLET'),
(8, 'Samsung Tab S9', 'Samsung Galaxy Tab S9 Ultra 5G', 25990000.00, 35, '/images/products/tab-s9.jpg', 'TABLET'),
(9, 'Apple AirPods Pro', 'AirPods Pro 2nd Generation with MagSafe Case', 6790000.00, 100, '/images/products/airpods.jpg', 'ACCESSORY'),
(10, 'Samsung Galaxy Watch', 'Galaxy Watch 5 Pro 45mm', 9990000.00, 60, '/images/products/galaxy-watch.jpg', 'ACCESSORY'),
(11, 'Anker PowerCore', 'Pin sạc dự phòng Anker PowerCore 20000mAh', 1200000.00, 200, '/images/products/anker.jpg', 'ACCESSORY'),
(12, 'iPhone 15 Pro Max MINI', 'abcd', 300000.00, 9, 'iphone15.jpg', 'PHONE');

-- Thêm dữ liệu vào bảng orders với trường phone_number
INSERT INTO `orders` (`id`, `order_date`, `total_amount`, `status`, `customer_name`, `customer_email`, `phone_number`, `shipping_address`, `user_id`) VALUES
(1, '2025-03-11 23:03:40', 29990000.00, 'COMPLETED', 'Nguyễn Văn A', 'nguyenvana@email.com', '0978123456', 'Số 123 Đường ABC, Quận 1, TP.HCM', 3),
(2, '2025-03-11 23:03:40', 52990000.00, 'PROCESSING', 'Trần Thị B', 'tranthib@email.com', '0965234567', 'Số 456 Đường XYZ, Quận Cầu Giấy, Hà Nội', 3),
(3, '2025-03-11 23:03:40', 7990000.00, 'PENDING', 'Lê Văn C', 'levanc@email.com', '0912345678', 'Số 789 Đường DEF, TP. Đà Nẵng', 3);

-- Thêm dữ liệu vào bảng order_items
INSERT INTO `order_items` (`id`, `order_id`, `product_id`, `quantity`, `price`) VALUES
(1, 1, 1, 1, 29990000.00),
(2, 2, 4, 1, 52990000.00),
(3, 3, 9, 1, 6790000.00),
(4, 3, 11, 1, 1200000.00);

COMMIT;