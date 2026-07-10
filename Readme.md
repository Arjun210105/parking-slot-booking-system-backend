
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


# 🔐 Authentication & User Module

The Parking Slot Booking System uses **JWT (JSON Web Token)** based authentication.

After a successful login, the backend returns a JWT token. Every protected endpoint requires this token in the `Authorization` header.

```
Authorization: Bearer <JWT_TOKEN>
```

---

# Authentication Flow

```text
                 Register
                     │
                     ▼
             User Account Created
                     │
                     ▼
                   Login
                     │
                     ▼
          Email + Password Verified
                     │
                     ▼
              JWT Token Generated
                     │
                     ▼
      Client Stores JWT Token Securely
                     │
                     ▼
Authorization: Bearer <TOKEN>
                     │
                     ▼
           Protected API Access
```

---

# Supported Roles

| Role | Description |
|------|-------------|
| CUSTOMER | Can search parking lots, create bookings, cancel bookings, and view personal booking history. |
| PARTNER | Can create and manage parking lots, parking slots, and view bookings for owned parking lots. |
| ADMIN | Reserved for future administrative operations. |

---

# Authentication Endpoints

---

# 1. Register User

### Endpoint

```
POST /api/v1/users/register
```

### Authentication Required

❌ No

---

## Request Body

```json
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phoneNumber": "9876543210",
    "password": "Password@123",
    "dateOfBirth": "2002-01-15",
    "role": "CUSTOMER"
}
```

---

## Validation Rules

| Field | Validation |
|--------|------------|
| firstName | Required |
| lastName | Required |
| email | Valid email format and unique |
| phoneNumber | Must be unique |
| password | Must satisfy password policy |
| role | CUSTOMER or PARTNER |

---

## Success Response

**HTTP Status**

```
201 Created
```

Example Response

```json
{
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phoneNumber": "9876543210",
    "role": "CUSTOMER"
}
```

---

## Possible Errors

| Status | Reason |
|---------|--------|
| 400 | Invalid request data |
| 409 | Email already exists |
| 409 | Phone number already exists |

---

# 2. Login

### Endpoint

```
POST /api/v1/users/login
```

### Authentication Required

❌ No

---

## Request Body

```json
{
    "email": "john@example.com",
    "password": "Password@123"
}
```

---

## Success Response

**HTTP Status**

```
200 OK
```

Example

```json
{
    "token": "<JWT_TOKEN>",
    "type": "Bearer",
    "expiresIn": 86400000,
    "role": "CUSTOMER",
    "firstName": "John",
    "lastName": "Doe"
}
```

> **Note:** Replace this response structure with your actual `LoginResponse` DTO if it differs.

---

## Possible Errors

| Status | Reason |
|---------|--------|
| 400 | Invalid request |
| 401 | Invalid email or password |
| 403 | Account disabled |

---

# JWT Authentication

After login the backend returns a JWT token.

Example:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

The client should include it in every protected request.

Example:

```
Authorization: Bearer eyJhbGc...
```

---

# Protected Endpoints

The following APIs require authentication.

| Module | Authentication |
|---------|----------------|
| Parking Lots | ✅ |
| Parking Slots | ✅ |
| Bookings | ✅ |
| Partner APIs | ✅ |

---

# Role Authorization

The backend uses Spring Security Method-Level Authorization.

Examples:

```java
@PreAuthorize("hasRole('CUSTOMER')")
```

```java
@PreAuthorize("hasRole('PARTNER')")
```

```java
@PreAuthorize("hasRole('ADMIN')")
```

---

# Token Lifecycle

```text
Login
   │
   ▼
JWT Generated
   │
   ▼
Frontend Stores Token
   │
   ▼
Token Sent With Every Request
   │
   ▼
JWT Filter Validates Token
   │
   ▼
Spring Security Authenticates User
   │
   ▼
Controller Executes
```

---

# Frontend Responsibilities

The frontend should:

- Store the JWT securely.
- Send the JWT in the `Authorization` header.
- Redirect unauthenticated users to the Login page.
- Redirect users after login based on role.
- Remove the token on logout.
- Handle `401 Unauthorized` by redirecting to Login.
- Handle `403 Forbidden` by showing an Access Denied page.

---

# AI Integration Notes

When generating the frontend:

- Maintain separate dashboards for CUSTOMER and PARTNER.
- Protect routes based on the user's role.
- Persist JWT authentication across page refreshes.
- Automatically attach the JWT token to every protected API request.
- Handle expired or invalid tokens gracefully.
- Never expose the JWT in the UI.

---

# 🅿️ Parking Lot & Parking Slot APIs

This module allows **Parking Partners** to create and manage parking lots and parking slots.

Only authenticated users with the **PARTNER** role are allowed to perform create, update, or delete operations.

Customers can only view parking lots and parking slots.

---

# Parking Lot Module

A Parking Lot represents a physical parking location.

Example:

```
City Center Mall Parking
```

Each Parking Lot contains one or more Parking Slots.

Relationship:

```
Parking Lot
      │
      ├── Slot A1
      ├── Slot A2
      ├── Slot A3
      └── ...
```

---

# Parking Lot Entity

| Field | Description |
|--------|-------------|
| id | Unique Parking Lot ID |
| name | Parking lot name |
| description | Parking lot description |
| address | Complete address |
| city | City |
| state | State |
| pincode | Postal code |
| latitude | GPS latitude |
| longitude | GPS longitude |
| totalSlots | Total parking capacity |
| availableSlots | Current available slots (maintained by backend) |
| owner | Parking partner |

---

# 1. Create Parking Lot

## Endpoint

```
POST /api/v1/parking-lots
```

Authentication

```
PARTNER
```

---

## Request Body

```json
{
    "name": "Central Mall Parking",
    "description": "Covered parking with CCTV",
    "address": "MG Road",
    "city": "Hyderabad",
    "state": "Telangana",
    "pincode": "500081",
    "latitude": 17.4435,
    "longitude": 78.3772,
    "totalSlots": 100
}
```

---

## Success Response

```
201 Created
```

Example

```json
{
    "id": 1,
    "name": "Central Mall Parking",
    "city": "Hyderabad",
    "totalSlots": 100,
    "availableSlots": 100
}
```

---

## Validation

- Name is required
- Address is required
- City is required
- State is required
- Pincode is required
- Total Slots must be greater than zero

---

## Possible Errors

| Status | Reason |
|---------|--------|
|400|Invalid Request|
|401|Authentication Required|
|403|Only PARTNER can create parking lots|

---

# 2. Get All Parking Lots

## Endpoint

```
GET /api/v1/parking-lots
```

Authentication

```
Required
```

---

## Success Response

```
200 OK
```

Returns all parking lots visible to the user.

---

# 3. Get Parking Lot By ID

## Endpoint

```
GET /api/v1/parking-lots/{parkingLotId}
```

Authentication

```
Required
```

---

## Success Response

```
200 OK
```

Returns complete details of the selected parking lot.

---

## Possible Errors

| Status | Reason |
|---------|--------|
|404|Parking Lot Not Found|

---

# 4. Update Parking Lot

## Endpoint

```
PUT /api/v1/parking-lots/{parkingLotId}
```

Authentication

```
PARTNER
```

---

## Business Rules

- Only the owner can update.
- Another partner cannot update this parking lot.
- Admin support will be added later.

---

## Success Response

```
200 OK
```

---

# 5. Delete Parking Lot

## Endpoint

```
DELETE /api/v1/parking-lots/{parkingLotId}
```

Authentication

```
PARTNER
```

---

## Business Rules

- Only owner can delete.
- Associated parking slots are removed according to backend rules.

---

# Parking Slot Module

Each Parking Slot belongs to exactly one Parking Lot.

Relationship:

```
Parking Lot
      │
      ▼
Parking Slot
```

---

# Parking Slot Entity

| Field | Description |
|--------|-------------|
| id | Slot ID |
| slotNumber | Display number (A1, A2...) |
| vehicleType | Supported vehicle |
| pricePerHour | Hourly parking price |
| slotStatus | AVAILABLE / OCCUPIED / MAINTENANCE |
| parkingLot | Parent Parking Lot |

---

# 1. Create Parking Slot

## Endpoint

```
POST /api/v1/parking-slots
```

Authentication

```
PARTNER
```

---

## Request Body

```json
{
    "parkingLotId": 1,
    "slotNumber": "A-01",
    "vehicleType": "CAR",
    "pricePerHour": 50,
    "slotStatus": "AVAILABLE"
}
```

---

## Success Response

```
201 Created
```

```json
{
    "id": 10,
    "slotNumber": "A-01",
    "vehicleType": "CAR",
    "pricePerHour": 50,
    "slotStatus": "AVAILABLE"
}
```

---

## Validation

- Parking Lot must exist.
- Slot Number must be unique within the parking lot.
- Price must be greater than zero.
- Vehicle Type must be supported.

---

# 2. Get Parking Slots

## Endpoint

```
GET /api/v1/parking-slots
```

Authentication

```
Required
```

Returns all parking slots.

---

# 3. Get Parking Slot By ID

## Endpoint

```
GET /api/v1/parking-slots/{slotId}
```

Returns complete slot information.

---

# 4. Update Parking Slot

## Endpoint

```
PUT /api/v1/parking-slots/{slotId}
```

Authentication

```
PARTNER
```

---

## Business Rules

- Only owner of the parking lot can update.
- Slot Number uniqueness must be maintained.
- Vehicle Type may be updated.
- Hourly price may be updated.
- Slot Status may be updated.

---

# 5. Delete Parking Slot

## Endpoint

```
DELETE /api/v1/parking-slots/{slotId}
```

Authentication

```
PARTNER
```

---

## Business Rules

- Only owner can delete.
- Slot cannot violate backend integrity rules.

---

# Slot Status

| Status | Meaning |
|----------|---------|
|AVAILABLE|Operational and can accept bookings|
|OCCUPIED|Reserved for future physical occupancy support|
|MAINTENANCE|Temporarily unavailable for booking|

---

# Frontend Integration Notes

### Parking Lot List

Display:

- Name
- Address
- City
- Total Slots
- Available Slots

---

### Parking Lot Details

Display:

- Full Address
- Description
- Parking Slots
- Prices
- Supported Vehicle Types

---

### Partner Dashboard

Allow partner to:

- Create Parking Lot
- Edit Parking Lot
- Delete Parking Lot
- Create Parking Slot
- Update Parking Slot
- Delete Parking Slot

Customers should never see management actions.

---

# Next Section

The next part documents the complete Booking Module including:

- Create Booking
- Booking History
- Cancel Booking
- Partner Booking View
- Automatic Booking Completion
- Request & Response Examples
- Validation Rules
- Business Rules
- Error Responses




