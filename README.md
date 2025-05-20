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
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
