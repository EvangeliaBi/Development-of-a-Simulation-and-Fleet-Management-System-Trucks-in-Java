# Logistics Route Optimization Simulator

A Java-based application that simulates logistics operations and evaluates truck routing strategies in order to maximize projected profit over a six-month horizon.  
The project emphasizes **clean architecture**, **separation of concerns**, and **backend-oriented design principles**.

---

## Overview

This system models logistics decision-making through a clear separation between presentation, orchestration, and business logic.  
Although it includes a desktop UI, the core value of the project lies in its **backend-style simulation engine**, which is designed to be reusable, testable, and independent of the UI layer.

---

## Key Capabilities

- Route profit evaluation across multiple logistics routes
- Automatic selection of the most profitable route
- Deterministic simulation with fixed scheduling
- Stochastic simulation with randomized trips
- Revenue, cost, and profit analysis over a 6-month period
- Fully decoupled UI and business logic

---

## Architecture & Design

The application follows a strict **Model–View–Controller (MVC)** architecture and adheres to **SOLID principles**.

### Model (Business Layer)
- `SimulationEngine`
- Responsible for:
  - Route distance handling
  - Capacity planning
  - Trip scheduling
  - Revenue and cost calculation
  - Profit estimation
- Designed as a backend-style service class, independent of presentation logic

### View (Presentation Layer)
- Java Swing GUI
- Handles user interaction and result presentation only
- Contains no business logic

### Controller (Application Layer)
- Translates user actions into domain parameters
- Selects simulation mode (deterministic / stochastic)
- Coordinates execution of business logic
- Acts as an orchestration layer, similar to a REST controller

---

## Simulation Modes

### Deterministic Simulation
- Uses fixed, predefined schedules
- Produces reproducible results

### Stochastic Simulation
- Introduces randomized trip schedules
- Models real-world uncertainty and variability

### Automatic Route Optimization
- Evaluates all available routes
- Selects the route with the highest projected profit
- Synchronizes optimized results with the UI

---

## Data Management

- Routes and distances stored in `Map<String, Double>`
- Customers and trucks retrieved via DAO pattern
- Java Stream API used for filtering and transformations
- Domain data passed explicitly to the simulation engine

---

## Technologies & Engineering Concepts

- Java
- Java Swing
- MVC Architecture
- DAO Pattern
- SOLID Principles
- Java Streams & Collections
- Event Dispatch Thread (EDT) safety
- Backend-oriented service design

---

## Execution

1. Clone the repository
2. Open the project in IntelliJ IDEA or Eclipse
3. Run the `main` method
4. The application initializes safely on the Event Dispatch Thread

---

## Future Improvements

The current architecture allows for straightforward extension into a production-grade backend system:

- **REST API Exposure**  
  Replace the Swing UI with a Spring Boot REST API, allowing simulations to be triggered via HTTP endpoints.

- **Persistence Layer Integration**  
  Replace in-memory DAOs with JPA/Hibernate and a relational database (e.g., PostgreSQL).

- **Strategy Pattern for Simulations**  
  Extract deterministic and stochastic simulations into separate strategy implementations.

- **Parallel Simulation Execution**  
  Utilize multithreading or reactive streams to evaluate routes concurrently for performance scalability.

- **Configuration & Pricing Policies**  
  Externalize pricing rules and cost models via configuration files or policy services.

- **Automated Testing**  
  Add unit and integration tests (JUnit, Mockito) focused on the Simulation Engine.

- **Observability & Metrics**  
  Introduce logging, metrics, and execution tracing for simulation analysis.

---

## Why This Project (Backend Perspective)

This project demonstrates:
- Backend-style service orchestration
- Clean separation between application layers
- Testable and extensible domain logic
- Strong architectural discipline
- Readiness for migration to web-based systems

---

## Author

Evangelia Bibasi 
