
# 🚗 Parking Slot Booking System

A secure, scalable, and production-oriented Parking Slot Booking System built using **Java, Spring Boot, Spring Security, Spring Data JPA, Hibernate, and MySQL**.

The system allows customers to discover parking spaces, reserve slots for a specific time duration, manage bookings, and enables parking partners to manage their parking lots and monitor bookings. The project follows a layered architecture with role-based authentication and authorization using JWT.

---

# 📌 Project Objectives

The primary goal of this project is to provide a backend service for a smart parking platform where:

- Customers can reserve parking slots in advance.
- Parking partners can register and manage their parking facilities.
- The system prevents conflicting bookings.
- Booking lifecycle is managed automatically.
- APIs are designed to be consumed by Web, Mobile, or third-party applications.

This backend is designed with production-oriented practices including authentication, authorization, transaction management, scheduling, validation, and exception handling.

---

# ✨ Features

## Authentication & Authorization

- JWT-based Authentication
- Secure Login
- User Registration
- Password Encryption using BCrypt
- Role-Based Access Control
- Method-Level Security

Supported Roles:

- CUSTOMER
- PARTNER
- ADMIN

---

## Customer Features

- Register/Login
- View available parking lots
- View parking slots
- Create parking bookings
- View booking history
- Cancel future bookings

---

## Partner Features

- Register/Login
- Create Parking Lots
- Update Parking Lots
- Delete Parking Lots
- Create Parking Slots
- Update Parking Slots
- Delete Parking Slots
- View bookings for owned parking lots

---

## System Features

- Automatic booking completion using Scheduler
- Transaction Management
- Global Exception Handling
- Input Validation
- DTO-based API responses
- Entity Mapping using MapStruct
- Layered Architecture

---

# 🛠 Technology Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MapStruct
- Lombok

## Database

- MySQL

## Authentication

- JWT (JSON Web Token)

## Build Tool

- Maven

## Testing

- Postman

---

# 🏛 Architecture

The application follows a layered architecture.

```text
                Client
                   │
                   ▼
            REST Controllers
                   │
                   ▼
             Service Layer
                   │
                   ▼
           Repository Layer
                   │
                   ▼
               MySQL Database
````

Each layer has a single responsibility.

### Controller Layer

Responsible for:

* Receiving HTTP Requests
* Request Validation
* Returning HTTP Responses

No business logic is implemented inside controllers.

---

### Service Layer

Contains all business logic.

Examples:

* Booking validation
* Cancellation rules
* Authorization checks
* Pricing calculation
* Scheduler logic
* Transaction management

---

### Repository Layer

Responsible only for database access.

Uses Spring Data JPA repositories.

---

### Entity Layer

Represents database tables.

Examples:

* User
* ParkingLot
* ParkingSlot
* Booking

---

### DTO Layer

Separates internal entities from API contracts.

Examples:

* LoginRequest
* BookingRequest
* BookingResponse

---

### Mapper Layer

Converts between Entities and DTOs using MapStruct.

---

### Security Layer

Responsible for:

* JWT Authentication
* Authorization
* Security Filters
* Password Encryption
* Method-Level Security

---

# 📂 Project Structure

```text
src
└── main
    ├── java
    │   └── com.parking.parking_slot_booking_system
    │       ├── config
    │       ├── controller
    │       ├── dto
    │       ├── entity
    │       ├── enums
    │       ├── exception
    │       ├── mapper
    │       ├── repository
    │       ├── scheduler
    │       ├── security
    │       ├── service
    │       │    └── impl
    │       ├── util
    │       └── ParkingSlotBookingSystemApplication.java
    │
    └── resources
        ├── application.properties
        └── ...
```

---

# 🎯 Design Principles

The project follows the following software engineering principles:

* Layered Architecture
* Separation of Concerns
* Single Responsibility Principle
* Dependency Injection
* DTO Pattern
* Repository Pattern
* Service Pattern
* Transaction Management
* Role-Based Access Control

---

# 📖 Business Workflow Overview

## Customer Workflow

```text
Register/Login
        │
        ▼
Browse Parking Lots
        │
        ▼
Select Parking Slot
        │
        ▼
Create Booking
        │
        ▼
View Booking History
        │
        ▼
Cancel Booking (Before Start Time)
```

---

## Partner Workflow

```text
Register/Login
        │
        ▼
Create Parking Lot
        │
        ▼
Create Parking Slots
        │
        ▼
Manage Parking Information
        │
        ▼
View Customer Bookings
```

---

## Booking Lifecycle

```text
BOOKED
   │
   ├────────────► CANCELLED
   │
   ▼
Scheduler
   │
   ▼
COMPLETED
```

A booking starts in the **BOOKED** state. A customer may cancel it before the booking starts. If it is not cancelled, the scheduler automatically marks it as **COMPLETED** after the booking end time has passed.

---

# 🔐 Current Security Model

Authentication is handled using JWT.

Every protected request must include:

```http
Authorization: Bearer <JWT_TOKEN>
```

Roles supported by the system:

| Role     | Description                              |
| -------- | ---------------------------------------- |
| CUSTOMER | Creates and manages personal bookings    |
| PARTNER  | Manages owned parking lots and slots     |
| ADMIN    | Administrative access (future expansion) |

Method-level authorization is implemented using Spring Security annotations.

---

# 📌 Current Project Status

The backend currently supports:

* User Authentication
* Parking Lot Management
* Parking Slot Management
* Booking Creation
* Booking Cancellation
* Booking History
* Partner Booking View
* Automatic Booking Completion
* Transaction Management

The backend is ready for frontend integration.

---

# 📚 Upcoming Documentation

The following sections are documented separately:

* Authentication API
* User APIs
* Parking Lot APIs
* Parking Slot APIs
* Booking APIs
* Database Schema
* Error Responses
* Frontend Integration Guide
* Environment Variables
* Deployment Guide
* Future Roadmap

```
```
