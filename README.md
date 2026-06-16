# Personal Finance Tracker (Backend)
## Overview

A backend application for personal finance management built with Java 17 and Spring Boot.
The system provides a REST API for tracking income, expenses, categories, and financial statistics.

The project is designed as a modular backend service and can be extended with a web or mobile frontend in the future.

## Features

### Transaction Management
- Create, update, delete transactions
- Retrieve list of transactions
- Each transaction includes:
    - amount
    - type (INCOME / EXPENSE)
    - category
    - date
    - optional description

### Category Management
- Create and manage categories (e.g. Food, Rent, Salary)
- Retrieve all categories
- Delete categories

### Balance Calculation
- Real-time balance computation
- Formula:
    - balance = total income − total expenses

### Statistics
- Total income
- Total expenses
- Expense distribution by category
- (optional) filtering by time period

###  Filtering
Transactions can be filtered by:

- date range
- category
- transaction type (INCOME / EXPENSE)

## Tech Stack
- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL (production) / H2 (development)
- REST API architecture

## Architecture
The project follows a classic layered architecture:

- Controller Layer – REST endpoints
- Service Layer – business logic
- Repository Layer – data access (JPA)

Designed for simplicity, maintainability, and future scalability.

## Data Model

### Transaction
- id
- amount
- type (INCOME / EXPENSE)
- category_id
- date
- description

### Category
- id
- name

## Testing

The application is extensively tested using JUnit.

- Unit tests for business logic
- Controller/API tests
- Test coverage above 99%

## Future Improvements
- JWT-based authentication
- User management (multi-user support)
- Advanced analytics dashboard
- Export to CSV / Excel
- Frontend integration (React / mobile app)
- Docker deployment

## Purpose

This project was created to practice:

- backend development with Spring Boot
- REST API design
- layered architecture
- working with relational databases (PostgreSQL)

## Running the Project

1. Clone repository
2. Configure PostgreSQL
3. Run:

mvn spring-boot:run
