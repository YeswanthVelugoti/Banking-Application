# 🏦 Banking Management System (Spring Boot + MySQL)

This is a modular backend banking application built with **Java**, **Spring Boot**, **Spring Data JPA**, and **MySQL**, supporting core features like user registration, account management, transaction history, and loan processing. The system is structured using a layered architecture and exposes a RESTful API for all operations.

---

## 📌 Features

- Create, update, delete, and search users
- Open and manage bank accounts
- Transfer funds between accounts with transaction logging
- Apply for and repay loans
- View transaction history with filters
- Field validations with DTOs and custom error messages
- RESTful API structure with clear endpoints

---

## 🧱 Architecture

The application follows a layered architecture:

- `Controller Layer` – Exposes endpoints via `@RestController`
- `Service Layer` – Handles business logic, validations
- `Repository Layer` – Manages database access using Spring Data JPA
- `DTOs` – Used for input validation and response formatting
- `Entities` – Represent DB tables with JPA annotations
- `Exception Handling` – Global error handling with `@ControllerAdvice`

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **Maven**
- **Postman / Insomnia** for API testing

---

## 🚀 Run Locally

1. Clone the project:
```bash
git clone https://github.com/YeswanthVelugoti/Banking-Application.git
cd Banking-Application

2. Set up MySQL DB and update `application.properties`.

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the APIs using Postman or your browser.

---

## 📂 Sample Endpoints

- `POST /users` – Create user
- `GET /users` – List users
- `POST /accounts` – Open bank account
- `POST /transfer` – Transfer funds
- `POST /transactions` – Add transaction
- `POST /loans/apply` – Apply for loan

---

## 🔐 Validation & Error Handling

- DTOs with `@NotNull`, `@Email`, `@Size`, etc.
- Global exception handling using `@ControllerAdvice`
- Custom error messages for all validation failures

---

## ✅ Future Enhancements

- JWT-based authentication and user roles
- Swagger/OpenAPI documentation
- Frontend client using Angular
- 
## 👨‍💻 Author

**Yeswanth Sai Kumar Velugoti**  
[Email](mailto:yeswanthvelugoti@gmail.com) • [LinkedIn](https://www.linkedin.com/in/yeswanth-velugoti-4290741a0/)

