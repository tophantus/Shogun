# Shogun – Web-based Sales Application


## Overview

Shogun is a modern, full-stack web-based sales application providing a seamless shopping experience. It features a secure backend built with Spring Boot and a responsive React frontend. Users can browse products, upload product images, authenticate securely, and pay online with Stripe.
Admins have additional privileges to add, edit, and delete users, while regular users can only browse products, place orders, and complete payments.

---

## Features

- RESTful APIs with full CRUD for product management using Spring Boot and Spring Data JPA
- MySQL database designed with JPA entities for reliable data persistence
- Responsive and intuitive UI built with React
- Secure authentication and authorization using Spring Security with JWT tokens
- Google OAuth 2.0 integration for Gmail login
- Stripe API integration for secure checkout and payment processing
- Cloudinary integration for efficient image upload and management

---

## Technology Stack

| Layer             | Technology & Tools                   |
|-------------------|------------------------------------|
| Backend           | Java, Spring Boot, Spring Security, Spring Data JPA, JWT, OAuth 2.0 |
| Frontend          | React, Axios , React Router, React-admin,|
| Database          | MySQL                              |
| Authentication    | JWT, Spring Security, Google OAuth 2.0 |
| Payment Gateway   | Stripe API                         |
| Image Storage     | Cloudinary API                     |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Node.js & npm
- MySQL
- Stripe account
- Cloudinary account
- Google Cloud project with OAuth 2.0 credentials

### Backend Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/tophantus/Shogun.git
    cd Shogun/backend
    ```
2. Configure environment variables or `application.properties` with your:
    - MySQL database credentials
    - JWT secret key
    - Google OAuth client ID and secret
    - Stripe API keys
    - Cloudinary cloud name, API key, and secret

3. Build and run backend server:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

### Frontend Setup

1. Navigate to frontend folder:
    ```bash
    cd ../frontend
    ```
2. Install dependencies:
    ```bash
    npm install
    ```
3. Add any required environment variables (such as API base URL) in `.env`
4. Start the React development server:
    ```bash
    npm start
    ```

---

## Usage

- Browse and manage products with full CRUD features
- Upload product images with Cloudinary integration
- Authenticate securely with email/password or Google OAuth
- Process payments safely with Stripe checkout

---

## Project Structure

```
Shogun/
├── backend/     # Spring Boot backend source code
├── frontend/    # React frontend source code
└── README.md    # Project documentation
```

---

## Contribution

This project is an individual work. Feel free to open issues or submit pull requests.
