# Pricing Service API

A Spring Boot-based service for retrieving and managing pricing information for products based on brand and date. The service allows querying prices for a specific product and brand within a given date range, providing flexibility to work in high concurrency environments.

## Table of Contents
1. [About](#about)
2. [Installation and Usage](#installation)
3. [API Documentation](#api-documentation)
4. [Testing](#testing)

## About
- Uso este espacio para comentar ciertas aclaraciones, en un principio he desarrollado este microservicio para que sea lo mas escalable posible, haciendo una separación en capas.
Un poco con esa intencion he incluido ciertos bloques de código que aunque no se pedian (y aunque ahora mismo no se utilizan) sirven para representar el diseño de la aplicacion que tenia en mente.

   Ejemplos de ello son mapeos, creados que no se utilizan porque no son necesarios en PriceMapper, PricePolicy o la funcion isValidAt dentro de Price.

- Entiendo además que el formato de las fechas indicado es el que se espera tanto como entrada, como salida ya que en h2 no deja cargar ese formato de fechas como timestamp. (Otra opción hubiera sido dar de alta los campos fecha como string en BBDD, aunque no he optado por esta opción)
### Features:
- **Query for prices** based on product, brand, and date.
- Handles **high concurrency** scenarios effectively.
- Returns price details with the highest priority, ensuring the most relevant price is returned.
- Implements a **global exception handler** for consistent error responses.
- Documentation available via **Swagger UI**.
- Docker functionality

## Installation

Follow these steps to set up the project on your local machine:

### Prerequisites
- Java 17 or higher
- Maven 3.x
- Database (MySQL or PostgreSQL)

### Steps to Install
1. **Clone the repository**:
   ```bash
   git https://github.com/robertoferrandez/pricing-service.git
   cd pricing-service
   
2. **Build and Run the Application**
    ```bash
    mvn clean install
    mvn spring-boot:run or docker-compose up --build

3. **Access the API via http://localhost:8080**
    ```bash
    http://localhost:8080/swagger-ui/index.html

## Api Documentation

This section provides detailed information about the available API endpoints and how to interact with them.

### **Get Applicable Price**
**Endpoint:** `GET /price`

Retrieve the applicable price for a specific product and brand on a given date.

#### Query Parameters:
- `date` (required): The date in `yyyy-MM-dd-HH.mm.ss` format. Example: `2022-01-01-12.00.00`.
- `product_id` (required): The unique ID of the product.
- `brand_id` (required): The unique ID of the brand.

#### Response:
- **200 OK**: Returns the price details in JSON format.
- **400 Bad Request**: Returns an error message if a required parameter is missing.
- **404 Not Found**: Returns an error message if no valid price is found.

- **Example request:**
    ```bash
    GET http://localhost:8080/prices?date=2022-01-01-12.00.00&product_id=35455&brand_id=1

## Testing

To ensure the proper functionality of the Pricing Service, you can test both the API and the service layer. This section outlines the testing methods, tools, and commands for running the tests.

### Manual Testing (API)

To manually test the Pricing Service API, you can use tools like **Postman** or **curl**.

#### Testing with Postman:
1. Open Postman and create a new `GET` request.
2. Enter the following URL: `http://localhost:8080/prices`.
3. Add the required query parameters:
    - `date`: A date in the format `yyyy-MM-dd-HH.mm.ss`, e.g., `2022-01-01-12.00.00`.
    - `product_id`: The ID of the product (e.g., `35455`).
    - `brand_id`: The ID of the brand (e.g., `1`).
4. Click **Send** and verify the response.
    - A successful response will return the price details in JSON format.

### Example request in Postman:
    GET http://localhost:8080/price?date=2022-01-01-12.00.00&product_id=35455&brand_id=1

### Automated Testing

If you want to run automated tests for the service layer, the project includes unit and integration tests using **JUnit**. You can run them using Maven:

 Run all tests:
   ```bash
   mvn test