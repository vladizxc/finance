1. Introduction
1.1 Purpose

Приложение предназначено для отслеживания личных финансов: доходов, расходов, баланса и анализа трат.

1.2 Scope

Backend-приложение на Java + Spring Boot, предоставляющее REST API для:

учета транзакций
категорий
просмотра статистики

2. Overall Description
2.1 Product Perspective

Standalone backend-приложение.
В будущем можно расширить:

web frontend
mobile app
2.2 User Classes
Single User (ты) — без сложной системы ролей

3. Functional Requirements
3.1 Transaction Management

Система должна позволять:

Создавать транзакцию
Получать список транзакций
Удалять транзакцию
Редактировать транзакцию

Transaction включает:

amount
type (INCOME / EXPENSE)
category
date
description (optional)

3.2 Category Management
Создавать категории (например: Food, Rent, Salary)
Получать список категорий
Удалять категории
3.3 Balance Calculation

Система должна:

Показывать текущий баланс

Считать:

balance = sum(income) - sum(expense)

3.4 Statistics
Общая сумма расходов
Общая сумма доходов
Расходы по категориям
(опционально) за период

3.5 Filtering
Фильтр по:
дате
категории
типу (income/expense)

4. Non-Functional Requirements

4.1 Technology
Java 17+
Spring Boot
Spring Data JPA
PostgreSQL (или H2 на старте)


4.2 Performance
Ответ API < 500ms (для локального приложения более чем)

4.3 Security
Пока можно без auth
Позже: JWT authentication

4.4 Maintainability
Использовать слои:
Controller
Service
Repository

5. Data Model (Initial)
Transaction
id
amount
type
category_id
date
description
Category
id
name
