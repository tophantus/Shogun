# 🛒 Shogun – Ứng dụng Bán hàng Trực tuyến

## 🧾 Tổng quan

**Shogun** là một ứng dụng web hiện đại, cung cấp trải nghiệm mua sắm liền mạch. Backend được xây dựng bằng Spring Boot, frontend bằng React.  
Người dùng có thể duyệt sản phẩm, tải ảnh lên, đăng nhập an toàn và thanh toán online qua Stripe.  
👑 Quản trị viên có quyền quản lý người dùng, trong khi người dùng thường chỉ có thể duyệt sản phẩm, đặt hàng và thanh toán.

---

## ✨ Tính năng

- 🔄 API RESTful hỗ trợ đầy đủ CRUD cho quản lý sản phẩm bằng Spring Boot và Spring Data JPA  
- 🛢️ Cơ sở dữ liệu MySQL thiết kế với JPA entities để đảm bảo lưu trữ dữ liệu bền vững  
- 💻 Giao diện trực quan, phản hồi nhanh được xây dựng với React  
- 🔐 Bảo mật xác thực và phân quyền bằng Spring Security kết hợp JWT  
- 📧 Đăng nhập bằng tài khoản Gmail thông qua Google OAuth 2.0  
- 💳 Tích hợp Stripe API để thanh toán trực tuyến an toàn  
- 🖼️ Tích hợp Cloudinary để tải lên và quản lý ảnh sản phẩm hiệu quả  

---

## 🧰 Công nghệ sử dụng

| 🧩 Thành phần        | 🚀 Công nghệ & Công cụ                                    |
|----------------------|-----------------------------------------------------------|
| 🔧 Backend           | Java, Spring Boot, Spring Security, Spring Data JPA, JWT, OAuth 2.0 |
| 🎨 Frontend          | React, Axios, React Router, React-admin                   |
| 🛢️ Cơ sở dữ liệu     | MySQL                                                     |
| 🔐 Xác thực          | JWT, Spring Security, Google OAuth 2.0                    |
| 💳 Thanh toán        | Stripe API                                                |
| ☁️ Lưu trữ ảnh       | Cloudinary API                                            |

---

## 🚀 Bắt đầu

### ⚙️ Yêu cầu hệ thống

- ☕ Java 17 trở lên  
- 🔨 Maven  
- 🟢 Node.js & npm  
- 🛢️ MySQL  
- 💳 Tài khoản Stripe  
- ☁️ Tài khoản Cloudinary  
- 🔑 Dự án Google Cloud có OAuth 2.0 credentials  

### 🔧 Thiết lập Backend

1. Clone repository:
    ```bash
    git clone https://github.com/tophantus/Shogun.git
    cd Shogun/backend
    ```

2. Cấu hình các biến môi trường hoặc file `application.properties` với thông tin sau:
   - 🛢️ Thông tin kết nối cơ sở dữ liệu MySQL  
   - 🔐 Khóa bí mật JWT  
   - 🔑 Client ID và Secret của Google OAuth  
   - 💳 Stripe API Key  
   - ☁️ Cloud name, API key và API secret của Cloudinary  

3. Build và chạy server backend:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

### 💻 Thiết lập Frontend

1. Di chuyển đến thư mục frontend:
    ```bash
    cd ../frontend
    ```

2. Cài đặt dependencies:
    ```bash
    npm install
    ```

3. Thêm các biến môi trường cần thiết (ví dụ: địa chỉ API backend) vào file `.env`

4. Khởi động React development server:
    ```bash
    npm start
    ```

---

## 🧪 Sử dụng

- 🔍 Tìm kiếm và quản lý sản phẩm với đầy đủ chức năng CRUD  
- 🖼️ Tải ảnh sản phẩm lên nhờ tích hợp Cloudinary  
- 🔐 Đăng nhập an toàn bằng email/mật khẩu hoặc qua Google OAuth  
- 💳 Thanh toán an toàn thông qua Stripe Checkout  

---

## 🗂️ Cấu trúc Dự án



```
Shogun/
├── backend/     # Spring Boot backend source code
├── frontend/    # React frontend source code
└── README.md    # Project documentation
```

---

## 🤝 Đóng góp

Dự án này là sản phẩm cá nhân. Bạn có thể thoải mái tạo issue hoặc gửi pull request nếu muốn đóng góp.
