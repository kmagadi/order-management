# Order Management Platform (Spring Cloud Microservices)

## Overview

The **Order Management Platform** is a distributed backend system built using **Spring Boot** and **Spring Cloud** that demonstrates a production-style microservices architecture. The project simulates a backend system similar to those used in **fintech, trading systems, and large-scale transaction processing platforms**.

The application was initially designed as a **monolithic Spring Boot application** and was later refactored into a **microservices-based architecture** to improve scalability, service isolation, and maintainability.

The platform includes multiple independent services communicating through **service discovery and API gateway routing** while enforcing **JWT-based authentication and role-based authorization**.

Key capabilities include:

- JWT Authentication
- API Gateway Routing
- Service Discovery using Eureka
- Inter-service Communication using OpenFeign
- Role-based Authorization
- Asynchronous Order Processing
- Analytics Microservice
- Global Exception Handling
- Centralized Logging with Spring AOP

This project demonstrates several backend engineering concepts used in real-world distributed systems.

---

# System Architecture

The system follows a **microservices architecture** with centralized routing and service discovery.

```
                        +----------------------+
                        |      API Gateway     |
                        |        :8080         |
                        +----------+-----------+
                                   |
             +---------------------+----------------------+
             |                                            |
             v                                            v
     +---------------+                           +----------------+
     |   Auth Service |                           |  Order Service |
     |      :8081     |                           |      :8082     |
     +-------+--------+                           +--------+-------+
             |                                             |
             |                                             |
             |                                     +-------+--------+
             |                                     | Analytics Service |
             |                                     |       :8083      |
             |                                     +------------------+
             |
     +--------------------+
     |    Eureka Server   |
     |       :8761        |
     +--------------------+
```

---

# Microservices

## Eureka Server

The **Eureka Server** acts as the service registry where all microservices register themselves.

Responsibilities:

- Service registration
- Service discovery
- Enables API Gateway to dynamically locate services

Annotation used:

```java
@EnableEurekaServer
```

Port:

```
8761
```

Dashboard:

```
http://localhost:8761
```

---

# API Gateway

The **API Gateway** is the single entry point for all external requests.

Responsibilities:

- Routing client requests to correct services
- Validating JWT tokens
- Injecting user context headers
- Acting as a centralized access point

Routes configured:

```
/auth/**      → AUTH-SERVICE
/orders/**    → ORDER-SERVICE
/analytics/** → ANALYTICS-SERVICE
```

Port:

```
8080
```

---

# Auth Service

The **Auth Service** manages user authentication and JWT token generation.

Responsibilities:

- User login
- Token generation
- Role assignment

Endpoint:

```
POST /auth/login
```

Example Request

```json
{
  "username": "admin",
  "password": "password"
}
```

Example Response

```json
{
  "token": "jwt-token",
  "role": "ADMIN"
}
```

Supported roles:

```
ADMIN
TRADER
VIEWER
```

JWT token contains:

- username
- role
- expiration timestamp

---

# Order Service

The **Order Service** manages order creation, validation, storage, and processing.

Responsibilities:

- Accept order requests
- Validate order data
- Store order information
- Process orders asynchronously
- Provide order retrieval API

Endpoints:

```
POST /orders
GET  /orders
```

Order Model Fields

```
id
customerId
product
orderType
quantity
price
status
```

Order Types

```
BUY
SELL
```

Order Status

```
CREATED
PROCESSING
COMPLETED
FAILED
```

Orders are stored in-memory using:

```
ConcurrentHashMap
```

---

# Order Validation

Validation is implemented using **polymorphism**.

Validators include:

```
OrderValidator
QuantityValidator
PriceValidator
```

Each validator implements:

```
validate(Order order)
```

---

# Asynchronous Processing

Order processing is executed asynchronously using:

```
@Async
```

This simulates real-world event-driven execution used in financial systems.

---

# Analytics Service

The **Analytics Service** calculates insights based on order data.

This service retrieves order data from **Order Service using OpenFeign**.

Endpoints:

```
GET /analytics/summary
GET /analytics/buy-vs-sell
GET /analytics/top-customer
```

Example Responses

Summary

```json
{
  "totalOrders": 10
}
```

Buy vs Sell

```json
{
  "BUY": 6,
  "SELL": 4
}
```

Top Customer

```
CUST-101
```

Analytics computations use **Java Streams**.

---

# Inter-Service Communication

Services communicate using **Spring Cloud OpenFeign**.

Example Feign Client:

```java
@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {

    @GetMapping("/orders")
    List<Map<String, Object>> getAllOrders(
        @RequestHeader("X-Role") String role
    );
}
```

Feign resolves service location using **Eureka service discovery**.

---

# Security

Authentication Flow

```
Client
   ↓
API Gateway
   ↓
Auth Service
   ↓
JWT Token Issued
   ↓
Client sends token with request
   ↓
Gateway validates token
   ↓
Request forwarded to services
```

Authorization rules:

```
ADMIN   → Full access
TRADER  → Order operations
VIEWER  → Analytics endpoints
```

Headers used:

```
Authorization: Bearer <token>
X-Role: ADMIN/TRADER/VIEWER
```

---

# Logging

Centralized logging is implemented using **Spring AOP**.

Logging captures:

- Method entry
- Method exit
- Execution time
- Exceptions

Example log output:

```
Entering: AnalyticsServiceImpl.getSummary()
Exiting: AnalyticsServiceImpl.getSummary() executed in 16 ms
```

This approach prevents logging code from polluting business logic.

---

# Global Exception Handling

Each service implements centralized exception handling using:

```
@RestControllerAdvice
```

Handled exceptions include:

```
OrderException
OrderNotFoundException
ValidationException
RuntimeException
```

Example Error Response

```json
{
  "error": "Access denied",
  "time": "2026-03-11T10:45:00"
}
```

---

# Project Structure

```
order-management-platform
│
├── api-gateway
│
├── auth-service
│   ├── controller
│   ├── service
│   ├── dto
│   └── security
│
├── order-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   ├── validation
│   ├── processor
│   ├── exception
│   └── aspect
│
├── analytics-service
│   ├── controller
│   ├── service
│   ├── client
│   └── aspect
│
└── eureka-server
```

---

# Running the Application

Start services in the following order:

1. Eureka Server
2. Auth Service
3. Order Service
4. Analytics Service
5. API Gateway

Service Ports

| Service | Port |
|--------|------|
Eureka Server | 8761 |
Auth Service | 8081 |
Order Service | 8082 |
Analytics Service | 8083 |
API Gateway | 8080 |

---

# Example Workflow

1. User logs in

```
POST /auth/login
```

2. JWT token is returned

3. User places order

```
POST /orders
```

4. Orders are processed asynchronously

5. Analytics service retrieves order data

```
GET /analytics/summary
```

6. Feign client calls Order Service internally

---

# Key Features Demonstrated

- Microservices architecture
- Service discovery
- API Gateway routing
- JWT authentication
- Role-based authorization
- OpenFeign communication
- Asynchronous processing
- AOP logging
- Global exception handling
- Clean layered architecture

---

# Possible Future Enhancements

Potential improvements:

- Distributed tracing
- Circuit breaker with Resilience4j
- Kafka event-driven communication
- Redis caching
- Docker containerization
- Kubernetes deployment
- Centralized logging with ELK stack
