# 🛍️ ShopCart Pro — Full Stack E-Commerce Application

**Tech Stack:**  
Java 17 · Spring Boot 3 · Spring Data JPA · MySQL · React

ShopCartPro is a full-stack e-commerce project built with Java + Spring Boot (Backend) and React + Redux Toolkit Query (Frontend).
It demonstrates modular REST APIs, state management, and seamless frontend-backend integration for modern web applications

---

## 🧱 Tech Stack Overview

**Language:** Java 17  
**Framework:** Spring Boot 3.x  
**Database:** MySQL 8.x  
**ORM:** Hibernate (JPA)  
**Frontend (connected project):** React + Redux Toolkit Query  
**Build Tool:** Maven  
**Server:** Tomcat 10 (Embedded)  
**Testing:** Postman / JUnit (Planned)


---

## 🚀 Features
- 🧩 Modular Backend (Spring Boot REST APIs)
  - CRUD operations for products
  - Category-based filtering
  - Cart and Order management
  - Ready for JWT-based authentication
- 💾 Database: MySQL with auto-loaded sample data
- 🎨 Frontend: React - https://github.com/VChandana/ShopCartPro-Frontend
  - Product listing, cart, checkout, and admin UI (to be added next)
- 🧪 Postman-ready APIs for testing

---

## 🏗️ Project Structure
```
shopcartpro-backend/
├── src/
│   ├── main/
│   │   ├── java/com/shopcartpro/
│   │   │   ├── controller/       # REST Controllers
│   │   │   ├── model/            # Entities (Product, Cart, Order)
│   │   │   ├── repository/       # JPA Repositories
│   │   │   └── service/          # Business Logic Layer
│   │   └── resources/
│   │       ├── application.properties  # MySQL config
│   │       └── data.sql                # Sample Product Data
│   └── test/                           # JUnit Tests (optional)
└── pom.xml
```

---

## ⚙️ Backend Setup Guide

### 1️⃣ Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+

### 2️⃣ Clone and Build
```bash
git clone https://github.com/VChandana/ShopCartPro-Backend.git
cd shopcartpro-backend
mvn clean install
```

### 3️⃣ Configure Database
Create the database in MySQL:
```sql
CREATE DATABASE shopcartpro_db;
```

Update your `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopcartpro_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.sql.init.mode=always
```

### 4️⃣ Run Application
```bash
mvn spring-boot:run
```
Server runs at: [http://localhost:8080](http://localhost:8080)

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | /api/products | Get all products |
| GET | /api/products/{id} | Get product by ID |
| GET | /api/products/category/{category} | Filter by category |
| POST | /api/products | Add new product |
| PUT | /api/products/{id} | Update product |
| DELETE | /api/products/{id} | Delete product |

---

## 💾 Sample Data
The project loads 30 sample product records automatically from `data.sql`  
Categories include Electronics, Fashion, Books, Home, and Beauty.

---

## 🧪 Testing APIs (Postman)
Use Postman or browser:
- http://localhost:8080/api/products
- http://localhost:8080/api/products/category/Electronics


---

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.2-green)
![License](https://img.shields.io/badge/license-MIT-lightgrey)


---

## 📜 License
This project is licensed under the MIT License — free to use and modify.

---

## 👩‍💻 Author
**Chandana Vanam**  
Full Stack Developer | Java · Spring Boot · React
