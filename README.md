# Spring Auth Service

A secure back-end application using Java, Spring Boot, Spring Security, Hibernate, JWT and MySQL.

## 🛠 Tech Stack

### Backend
- Java
- Spring Boot 3
- Spring Security 6
- Hibernet/JPA
- JWT(Json Web Token)
- MySQL
- Gradle

## Features

**Authentication Module**
- User Registration
- User Login
- BCrypt Password Encryption
- JWT Token Generation
- JWT Validation

**User Details Module**
Authenticated users can:

- View Details
- Update Details
- Upload PDF
- Upload Video

## Project Structure

```
src/main/java/com/example/assignment
│
├── config
│   ├── JwtAuthFilter.java
│   └── SecurityConfig.java
│
├── controller
│   ├── AuthController.java
│   └── UserDetailsController.java
│
├── dto
│   ├── LoginReq.java
│   ├── RegisterReq.java
│   └── UserDetailsReq.java
│
├── entity
│   ├── User.java
│   └── UserDetails.java
│
├── repository
│   ├── UserRepository.java
│   └── UserDetailsRepository.java
│
├── service
│   ├── AuthService.java
│   ├── CustomUserDetailsService.java
│   ├── DetailsService.java
│   └── FileUploadService.java
│
└── utils
    └── JwtToken.java
    ├── response
    │   ├── ApiError.java
    │   └── ApiResponse.java
    └── exception
        ├── GlobalExceptionHandler.java
        └── ResourceNotFoundException.java
```

## Security Architecture
### Public APIs
```bash
POST /auth/register
POST /auth/login
```

### Protected APIs
```bash
GET    /details/{userId}
PUT    /details/{userId}

POST   /details/{userId}/upload/pdf
POST   /details/{userId}/upload/video
```

### All Protected APIs Required
```bash
Authorization: Bearer JWT_TOKEN
```

## Global Exception Handling
Standardized error response:
```bash
{
  "success": false,
  "message": "User Not Found"
}
```

## Standard Response
Successful response:
```bash
{
  "success": true,
  "message": "Details fetched successfully",
  "data": {}
}
```

## File Upload Support
Supported uploads:
- PDF Upload
- Video Upload

Files are stored locally in:
```bash
uploads/pdfs
uploads/videos
```

## Running The Project
### Run In IntelliJ IDEA

### Step 1 — Open Project
Open the project using:
```bash
IntelliJ IDEA
```

### Step 2 — Configure MySQL
Ensure MySQL server is running.

Create database:
```bash
CREATE DATABASE auth_db;
```

### Step 3 — Configure application.yml
Update:
```bash
src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/auth_db
    username: root
    password: yourpassword

  jpa:
    hibernate:
      ddl-auto: update

    show-sql: true

jwt:
  secret-key: your-secret-key
  expiration: 3600000

server:
  port: 8080
```

### Step 4 — Run Application
Open:
```bash
AssignmentApplication.java
```
Run using:
```bash
▶ Run AssignmentApplication
```
or click the green run button beside:
```bash
main()
```

### Step 5 — Verify Application Started
Expected console output:
```bash
Tomcat started on port(s): 8080
Started AssignmentApplication
```
