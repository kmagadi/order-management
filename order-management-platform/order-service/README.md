# High Throughput Order Management System

## Overview

This project implements a lightweight, high-throughput order management backend inspired by modern trading and e-commerce platforms. The system accepts orders via REST APIs, processes them concurrently, maintains in-memory order state, persists order activity to files, and provides real-time analytics.

The goal of this system is to simulate real-world backend infrastructure used in fintech, brokerage platforms, and large-scale e-commerce systems, with a focus on concurrency, scalability, fault tolerance, and clean architecture.

The system also demonstrates the use of **polymorphic validation using the Strategy Pattern**, allowing validation rules to be extended without modifying existing business logic.

---

## Key Features

* REST-based order ingestion using Spring Boot
* Concurrent order processing using multithreading
* Thread-safe in-memory storage using concurrent collections
* File-based audit logging using Java I/O
* Real-time analytics using Java Streams
* Polymorphic validation using validator strategy pattern
* Custom exception handling with descriptive error messages
* Non-blocking asynchronous processing
* Modular and clean layered architecture
* Easily extensible to event-driven and distributed systems

---

## System Architecture

### High-Level Architecture Diagram

```
                +------------------------+
                |      REST Clients      |
                | (Trading / E-commerce) |
                +------------------------+
                           |
                           v
                +------------------------+
                |     Order Controller   |
                +------------------------+
                           |
                           v
                +------------------------+
                |     Order Service      |
                | (Business Logic)       |
                +------------------------+
                           |
                           v
                +------------------------+
                |   Order Validators     |
                | (Polymorphic Strategy) |
                +------------------------+
                           |
                           v
                +------------------------+
                |   In-Memory Repository |
                |  (ConcurrentHashMap)   |
                +------------------------+
                           |
                           v
                +------------------------+
                |   Async Order Engine   |
                |    (Thread Pool)       |
                +------------------------+
                           |
          +----------------+----------------+
          |                                 |
          v                                 v
+------------------------+       +------------------------+
| Order Lifecycle        |       | File Logger            |
| (Processing Engine)    |       | (Audit & Compliance)   |
+------------------------+       +------------------------+
                                           |
                                           v
                                +------------------------+
                                | Analytics Service      |
                                | (Java Streams)         |
                                +------------------------+
```

---

## System Design Explanation

### 1. Order Ingestion

The system begins by accepting order requests through REST APIs. This simulates real-world order intake systems used in trading desks, payment platforms, and e-commerce backends.

Each request is validated and converted into a domain object representing an order.

---

### 2. Layered Architecture

The application follows a clean layered structure:

* Controller → Handles HTTP requests
* Service → Business logic and orchestration
* Validation → Polymorphic validation rules
* Repository → In-memory storage
* Processor → Asynchronous execution
* Analytics → Stream-based computation

This design promotes scalability, testability, and maintainability.

---

### 3. Polymorphic Order Validation

The system uses a **validator strategy pattern** to perform order validation.

A common interface `OrderValidator` defines the validation contract. Multiple validators implement this interface, such as:

* QuantityValidator
* PriceValidator

Spring automatically injects all validator implementations, allowing the system to validate orders using a **validation chain**.

This approach follows the **Open/Closed Principle (SOLID)**:

* New validation rules can be added without modifying existing code.
* Business logic remains clean and extensible.

---

### 4. Concurrent Order Processing

The system uses a fixed-size thread pool to process orders asynchronously.

Each worker:

* Updates order status
* Simulates execution or fulfillment
* Persists updates
* Logs audit records

This mimics real trading or order fulfillment engines where multiple streams process transactions concurrently.

---

### 5. Thread-Safe In-Memory Storage

Orders are stored in memory using `ConcurrentHashMap`.

This enables:

* Fast read/write operations
* Thread safety under concurrent load
* Real-time state tracking
* High throughput

This approach is commonly used in:

* Order books
* Risk engines
* Payment gateways
* Inventory systems

---

### 6. Order Lifecycle Management

Each order goes through a lifecycle:

* CREATED
* PROCESSING
* COMPLETED
* FAILED

This allows real-time monitoring and supports analytics and debugging.

---

### 7. File-Based Audit Logging

Every order event is persisted in a log file.

This ensures:

* Auditability
* Compliance
* Traceability
* Debugging and recovery

Such logging is mandatory in regulated environments like financial systems.

---

### 8. Real-Time Analytics

The analytics module uses Java Streams to compute insights such as:

* Total order value
* BUY vs SELL distribution
* Top customer by volume
* Order grouping by status

This demonstrates modern functional programming and real-time computation.

---

### 9. Fault Tolerance and Resilience

The system is designed to continue processing even when some orders fail.

Failures are:

* Logged
* Marked as FAILED
* Handled using custom exceptions with descriptive messages

This ensures system stability under unpredictable inputs.

---

### 10. Scalability and Extensibility

The architecture is modular and easily extensible to support:

* Event-driven processing
* Distributed order engines
* Database persistence
* Order matching engines
* Microservices architecture
* Cloud-native deployment
* Monitoring and observability

---

## Technology Stack

* Java 21
* Spring Boot
* Concurrent Programming
* Java Streams
* Java I/O
* Maven
* REST APIs

---

## Project Structure

```
src/main/java/com/karthik/ordermanagement
│
├── analytics
├── config
├── controller
├── dto
├── exception
├── model
├── processor
├── repository
├── service
├── util
└── validation
    ├── OrderValidator
    ├── QuantityValidator
    └── PriceValidator
```

---

## How to Run

### Prerequisites

* Java 21 or higher
* Maven

---

### Run the Application

```
mvn clean install
mvn spring-boot:run
```

The server will start on:

```
http://localhost:8080
```

---

### Sample API

Place an order:

```
POST /orders
```

Request body:

```
{
  "customerId": "CUST_001",
  "product": "AAPL",
  "orderType": "BUY",
  "quantity": 10,
  "price": 150
}
```

---

## Load Testing

The system supports concurrent load testing using multithreaded clients.

A Python script can be used to simulate multiple users sending requests simultaneously, validating system scalability and performance under high throughput.

---

## Future Improvements

* Kafka-based event-driven order ingestion
* Order matching engine
* Risk and compliance module
* Database persistence using JPA
* Distributed microservices architecture
* Monitoring and observability with Prometheus and Grafana
* Virtual threads for ultra-high throughput
* Caching and rate limiting
* Cloud deployment and containerization

---

## Conclusion

This project demonstrates the design and implementation of a high-performance, concurrent order processing system inspired by modern trading and e-commerce platforms. It showcases core backend engineering skills including concurrency, system design, polymorphism, fault tolerance, and scalable architecture.