# SolveStuff

A Spring Boot REST API that provides numerical methods for solving mathematical problems, including linear systems, root finding, and interpolation.

## Features

- **Direct Methods** – Gauss Elimination, Gauss-Jordan
- **Iterative Methods** – Jacobi, Gauss-Seidel
- **Root Finding** – Bisection, False Position (Falsi), Newton-Raphson, Secant
- **Interpolation** – Lagrange, Newton Divided Differences

## Requirements

- Java 17+
- Maven (or use the included `mvnw` wrapper)

## Running the Application

```bash
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`.

## API Documentation

Interactive API docs (Swagger UI) are available at:

```
http://localhost:8080/swagger-ui.html
```

## Endpoints

### Direct Methods

| Method | Endpoint |
|--------|----------|
| Gauss Elimination | `POST /direct/gauss-elimination` |
| Gauss-Jordan | `POST /direct/gauss-jordan` |

### Iterative Methods

| Method | Endpoint |
|--------|----------|
| Jacobi | `POST /iterative/jacobi` |
| Gauss-Seidel | `POST /iterative/seidel` |

### Root Finding

| Method | Endpoint |
|--------|----------|
| Bisection | `POST /root/bisection` |
| False Position | `POST /root/falsi` |
| Newton-Raphson | `POST /root/rapson` |
| Secant | `POST /root/secant` |

### Interpolation

| Method | Endpoint |
|--------|----------|
| Lagrange | `POST /interpol/lagrange` |
| Newton Divided | `POST /interpol/divided` |

## Example Requests

See [`curl.md`](curl.md) for the full list of `curl` commands covering every endpoint.

## Tech Stack

- Java 17
- Spring Boot 3
- Lombok
- springdoc-openapi (Swagger UI)
