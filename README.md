<h1 align="center">NovaSpend 💰</h1>
<p align="center">
  <strong>Smart Personal Finance & Expense Tracking System</strong><br>
  Built with Spring Boot + MongoDB
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-success" />
  <img src="https://img.shields.io/badge/MongoDB-Atlas-green" />
  <img src="https://img.shields.io/badge/Build-Passing-brightgreen" />
  <img src="https://img.shields.io/badge/License-MIT-yellow" />
</p>

---

## 📌 **Overview**

NovaSpend is a modern, efficient, and intuitive **personal finance tracking application** built with **Spring Boot**, **MongoDB**, and clean architecture principles.  
It allows users to manage expenses, categorize transactions, analyze spending patterns, and gain full visibility into their financial habits.

This project is ideal for:
- Students learning Spring Boot
- Developers practicing REST API design
- Portfolio showcase projects
- LinkedIn/GitHub highlight-worthy work

---

## 🚀 **Features**

### 🔹 Core Features
- Add income and expense entries  
- Categorize transactions (Food, Travel, Bills, etc.)  
- Edit / Delete entries  
- Add optional tags  
- Automatic timestamp & type handling  
- MongoDB NoSQL storage

### 🔹 Upcoming Enhancements
- JWT Authentication  
- AI Expense Insights (LLM-based)  
- Monthly/Yearly analytics dashboard  
- Export to Excel/PDF  
- Email reports  
- React or Angular frontend  

---

## 🛠️ **Tech Stack**

| Layer | Technology |
|-------|-------------|
| **Backend** | Java 17, Spring Boot 3.x |
| **Database** | MongoDB Atlas / Local MongoDB |
| **Build Tool** | Maven |
| **Security (Future)** | Spring Security + JWT |
| **Deployment (Future)** | Docker, Render, Railway |

## ⚙️ **Getting Started**

### 1️⃣ Clone the repo
```
git clone https://github.com/ashishw116/NovaSpend.git
cd NovaSpend
```
### 2️⃣Configure MongoDB

Update the MongoDB connection URI in `src/main/resources/application.properties`.

**For Local MongoDB:**
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/novaspend
```
**For MongoDB Atlas:**
```properties
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster-url/novaspend
```
### 3️⃣ Run the application
```
mvn spring-boot:run
```
## 🧭 Roadmap

The following features and enhancements are planned for future development:

* User authentication
* Charts & Analytics
* Multi-user support
* Interactive frontend (React)
* AI-powered expense summarizer
* Budget Planner Module

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1.  Fork the repository
2.  Create a feature branch (`git checkout -b feature/new-module`)
3.  Commit changes
4.  Open a Pull Request

---

## 👤 Author

**Ashish Wagh**

* GitHub: https://github.com/ashishw116
