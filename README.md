# NovaSpend – Expense Tracker Backend

NovaSpend is a backend REST API for an expense tracking application.  
It is built using **Spring Boot**, **JWT authentication**, **Redis caching**, **MongoDB**, and **Docker**, following clean architecture and production-ready practices.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot
- Spring Security (JWT + Refresh Token)
- MongoDB
- Redis
- Docker & Docker Compose
- Swagger / OpenAPI

---

## ✨ Features

- User authentication (Signup / Login)
- JWT-based security with Refresh Tokens
- Role-based access control (USER / ADMIN)
- Expense management (CRUD)
- Pagination & filtering
- Redis caching for performance
- Secure global exception handling
- Dockerized setup for easy deployment
- Swagger API documentation

---

## 🧱 Architecture Overview

- RESTful API design
- Stateless authentication using JWT
- Redis used for caching frequently accessed data
- MongoDB as the primary database
- Docker Compose for local production-like setup

---

## 🔐 Authentication Flow

1. User logs in using email & password  
2. Server returns:
   - Access Token (JWT)
   - Refresh Token  
3. Access Token is used for secured APIs  
4. Refresh Token is used to generate new Access Tokens  
5. Redis is used for caching user profile and finance data  

---

## 📦 API Documentation

Swagger UI is available at:

```
http://localhost:9090/swagger-ui.html
```

---

## 🐳 Run Project Using Docker

### Prerequisites
- Docker
- Docker Compose

### Steps

```bash
docker-compose up --build
```

### Application URL
```
http://localhost:9090
```

---

## ⚙️ Configuration

### application.properties
```properties
spring.application.name=NovaSpend
jwt.accessTokenExpiry=900
jwt.refreshTokenExpiry=604800
```

### application-prod.properties
```properties
server.port=9090
spring.data.mongodb.uri=mongodb://mongo:27017/novaspend
spring.data.mongodb.database=novaspend
spring.redis.host=redis
spring.redis.port=6379
```

### Environment Variables
```text
JWT_SECRET=your-secret-key
```

---

## 📁 Project Structure

```
src/main/java
 ├── controller
 ├── service
 ├── repository
 ├── security
 ├── exception
 ├── mapper
 ├── model
 └── utility
```

---

## 🛡️ Security Best Practices

- No sensitive credentials committed to the repository
- Generic error messages for authentication failures
- Stateless session management
- Secure password hashing using BCrypt

---

## 👨‍💻 Author

**Ashish Wagh**  
Backend Java Developer  
Spring Boot | MongoDB | Redis | Docker
