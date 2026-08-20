# Order Management Service

A REST API for managing products and orders, built with **Spring Boot 3** and **Java 17**.
Built as a hands-on project to apply modern Java backend practices — layered architecture,
transactional integrity, containerization, and PostgreSQL — alongside years of experience
in enterprise Java/J2EE development.

## Features

- **Product catalog management** — full CRUD with SKU uniqueness and price/stock validation
- **Order creation** — multi-item orders with server-computed pricing (client can never set its own price)
- **Stock control** — orders are rejected if requested quantity exceeds available stock
- **Order lifecycle** — status transitions (`PENDING → CONFIRMED → SHIPPED → DELIVERED`, or `CANCELLED`), with guards against illegal transitions (e.g. can't modify a `DELIVERED` or `CANCELLED` order)
- **Consistent error handling** — a global exception handler returns clean, structured JSON errors (404 for not found, 409 for conflicts like insufficient stock, 400 for validation failures) instead of raw stack traces
- **Transactional integrity** — order creation is atomic; verified that a partial failure (e.g. one item out of stock) rolls back the *entire* order, including items that would have succeeded on their own
- **Containerized** — Docker Compose setup running the app alongside a real PostgreSQL database; verified data persists across full container restarts

## Tech Stack

Java 17 · Spring Boot 3 · Spring Data JPA · Hibernate · PostgreSQL · H2 (local dev) · Maven · Docker · Docker Compose · Lombok

## Architecture

Each layer has one job. Controllers only translate HTTP ↔ Java objects. Services hold business
rules (stock checks, status transition rules, total calculation). Repositories talk to the database.

## Running Locally (H2, no Docker needed)

**Prerequisites:** Java 17+, Maven 3.9+

```bash
mvn spring-boot:run
```

API starts on `http://localhost:8080`, backed by an in-memory H2 database.

## Running with Docker Compose (PostgreSQL)

**Prerequisites:** Docker Desktop

```bash
docker-compose up --build
```

This starts two containers: a PostgreSQL database and the Spring Boot app, wired together, with
data persisted in a Docker volume across restarts.

## API Reference

### Products

| Method | Endpoint             | Description         |
|--------|-----------------------|----------------------|
| GET    | `/api/products`       | List all products    |
| GET    | `/api/products/{id}`  | Get one product      |
| POST   | `/api/products`       | Create a product     |
| PUT    | `/api/products/{id}`  | Update a product     |
| DELETE | `/api/products/{id}`  | Delete a product     |

**Create a product:**
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Webcam","sku":"SKU-005","price":2499.00,"stockQuantity":40}'
```

### Orders

| Method | Endpoint                    | Description                          |
|--------|------------------------------|---------------------------------------|
| GET    | `/api/orders`                | List all orders                       |
| GET    | `/api/orders?status=PENDING` | List orders filtered by status        |
| GET    | `/api/orders/{id}`           | Get one order                         |
| POST   | `/api/orders`                | Create an order                       |
| PATCH  | `/api/orders/{id}/status`    | Update order status                   |
| DELETE | `/api/orders/{id}`           | Delete an order                       |

**Create an order:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
        "customerName": "Ramesh Kumar",
        "items": [
          { "productId": 1, "quantity": 2 },
          { "productId": 2, "quantity": 1 }
        ]
      }'
```

**Update order status:**
```bash
curl -X PATCH http://localhost:8080/api/orders/1/status \
  -H "Content-Type: application/json" \
  -d '{"status":"CONFIRMED"}'
```

## Roadmap

- [ ] Unit tests (JUnit + Mockito) for service-layer business logic
- [ ] JWT-based authentication and role-based access control
- [ ] Pagination on list endpoints
- [ ] Swagger/OpenAPI documentation
- [ ] Deploy to AWS

## Author

Shwetha Kumar — Senior Technical Lead (Java/J2EE, Oracle Commerce ATG), currently expanding
into modern Spring Boot and cloud-native backend development.