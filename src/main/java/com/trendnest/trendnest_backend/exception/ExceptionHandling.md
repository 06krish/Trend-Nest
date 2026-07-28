# Exception Handling in Spring Boot

> Complete Beginner to Intermediate Notes

---

# Table of Contents

1. What is Exception Handling?
2. Why do we need Exception Handling?
3. Default Spring Boot Exception Handling
4. Custom Exceptions
5. RuntimeException vs Checked Exception
6. Global Exception Handler
7. @RestControllerAdvice
8. @ExceptionHandler
9. ErrorResponse DTO
10. HTTP Status Codes
11. Exception Flow
12. Best Practices
13. Common Mistakes
14. Interview Questions
15. Summary

---

# 1. What is Exception Handling?

Exception Handling is the process of handling runtime errors in an application without crashing it.

Instead of returning an ugly server error, we return a meaningful response to the client.

Example

Instead of

```text
500 Internal Server Error
```

we return

```json
{
    "status":404,
    "message":"Category not found"
}
```

---

# 2. Why do we need Exception Handling?

Imagine a client requests

```
GET /api/categories/100
```

But category **100** does not exist.

Without exception handling

```
Server Crash

OR

500 Internal Server Error
```

The client has no idea what happened.

With exception handling

```json
{
    "status":404,
    "message":"Category not found with id : 100"
}
```

Now the frontend knows exactly what happened.

---

# 3. Default Spring Boot Exception Handling

Without writing any code,

Spring automatically handles exceptions.

Example

```java
throw new RuntimeException("Something went wrong");
```

Response

```json
{
    "timestamp":"...",
    "status":500,
    "error":"Internal Server Error",
    "path":"/api/categories"
}
```

Although useful,

this response is

- Ugly
- Difficult for frontend
- Doesn't contain our business message

---

# 4. Custom Exceptions

Instead of

```java
throw new RuntimeException("Category already exists");
```

we create

```java
public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String message){
        super(message);
    }

}
```

Now

```java
throw new CategoryAlreadyExistsException("Category already exists");
```

The exception itself explains the problem.

---

Another example

```java
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message){
        super(message);
    }

}
```

Usage

```java
throw new ResourceNotFoundException(
        "Category not found with id : " + id);
```

---

# 5. RuntimeException vs Checked Exception

## Checked Exception

Compiler forces you to handle it.

Example

```java
IOException
SQLException
```

Must use

```java
try{

}catch(){

}
```

or

```java
throws IOException
```

---

## RuntimeException

Occurs while the program is running.

Compiler does not force handling.

Examples

```java
NullPointerException

ArithmeticException

IllegalArgumentException
```

Custom exceptions usually extend

```java
RuntimeException
```

because they represent business errors.

---

# 6. Global Exception Handler

Instead of writing

```java
try{

}catch(){

}
```

inside every controller,

Spring allows one global handler.

Example

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

}
```

Every controller automatically uses it.

---

# 7. @RestControllerAdvice

```java
@RestControllerAdvice
```

Meaning

> Handle exceptions thrown from every REST Controller.

Think of it as

```
All Controllers

↓

GlobalExceptionHandler

↓

JSON Response
```

Instead of

```
Controller A

Controller B

Controller C

Each having try-catch
```

one class handles everything.

---

# 8. @ExceptionHandler

Example

```java
@ExceptionHandler(CategoryAlreadyExistsException.class)
```

Meaning

Whenever

```java
CategoryAlreadyExistsException
```

is thrown,

execute this method.

Example

```java
@ExceptionHandler(CategoryAlreadyExistsException.class)
public ResponseEntity<ErrorResponse> handleException(
        CategoryAlreadyExistsException ex){

}
```

Spring automatically calls it.

---

# 9. ErrorResponse DTO

Instead of returning random JSON,

we created

```java
public class ErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

}
```

Now every API returns the same structure.

Example

```json
{
    "timestamp":"2026-07-28T20:30",
    "status":409,
    "error":"Conflict",
    "message":"Category already exists",
    "path":"/api/categories"
}
```

Advantages

- Consistent API
- Easy for frontend
- Professional design

---

# 10. HTTP Status Codes

| Status | Meaning | Usage |
|---------|---------|-------|
|200|OK|GET successful|
|201|Created|POST successful|
|204|No Content|DELETE successful|
|400|Bad Request|Validation failed|
|401|Unauthorized|Authentication required|
|403|Forbidden|Access denied|
|404|Not Found|Resource missing|
|409|Conflict|Duplicate resource|
|500|Internal Server Error|Unexpected error|

---

# 11. Exception Flow

```
Client

↓

Controller

↓

Service

↓

Repository

↓

Database

↓

Business Exception

↓

GlobalExceptionHandler

↓

ErrorResponse

↓

JSON Response
```

Example

```
Duplicate Category

↓

CategoryAlreadyExistsException

↓

GlobalExceptionHandler

↓

409 Conflict
```

---

# 12. Best Practices

✔ Create custom exceptions.

✔ Never expose stack traces to clients.

✔ Use ResponseEntity.

✔ Keep one ErrorResponse model.

✔ Return proper HTTP status codes.

✔ Keep exception messages meaningful.

✔ Use GlobalExceptionHandler.

✔ Log exceptions.

---

# 13. Common Mistakes

❌ Using RuntimeException everywhere.

Wrong

```java
throw new RuntimeException("Error");
```

Correct

```java
throw new CategoryAlreadyExistsException(
        "Category already exists");
```

---

❌ Returning 500 for every error.

Different errors should return different status codes.

404

409

400

401

403

500

---

❌ Writing try-catch in every controller.

Wrong

```java
@PostMapping

try{

}catch(){

}
```

Correct

Use

```java
@RestControllerAdvice
```

---

❌ Returning plain String

Wrong

```java
 return "Category not found";
```

Correct

Return

```json
{
    "status":404,
    "message":"Category not found"
}
```

---

# 14. Interview Questions

### What is Exception Handling?

Exception Handling is the mechanism of handling runtime errors gracefully without terminating the application.

---

### What is Global Exception Handling?

It is a centralized mechanism to handle exceptions thrown from multiple controllers using @RestControllerAdvice.

---

### Difference between try-catch and Global Exception Handler?

try-catch

- Local
- Repeated
- Hard to maintain

Global Exception Handler

- Centralized
- Reusable
- Cleaner

---

### Why create custom exceptions?

To make business errors meaningful and readable.

Example

```
CategoryAlreadyExistsException
```

is much more descriptive than

```
RuntimeException
```

---

### Why ResponseEntity?

It allows us to return

- HTTP Status
- Response Body
- Headers

---

### Why ErrorResponse?

To maintain one standard error format across the application.

---

### What does @ExceptionHandler do?

It tells Spring which method should handle a particular exception.

---

### What does @RestControllerAdvice do?

It handles exceptions thrown by all REST Controllers globally.

---

# 15. Summary

```
Business Error

↓

Custom Exception

↓

@RestControllerAdvice

↓

@ExceptionHandler

↓

ErrorResponse

↓

ResponseEntity

↓

JSON

↓

Frontend
```

---

# Project Structure

```
exception
│
├── CategoryAlreadyExistsException
├── ResourceNotFoundException
├── ErrorResponse
└── GlobalExceptionHandler
```

---

# Real Project Flow

```
React

↓

POST /api/categories

↓

CategoryController

↓

CategoryService

↓

Duplicate Category?

↓

YES

↓

CategoryAlreadyExistsException

↓

GlobalExceptionHandler

↓

409 Conflict

↓

React receives JSON
```

---

# Key Takeaways

- Exception Handling improves API reliability.
- Custom exceptions make code readable.
- `@RestControllerAdvice` provides centralized exception handling.
- `@ExceptionHandler` maps specific exceptions to handler methods.
- `ErrorResponse` keeps API responses consistent.
- Always return appropriate HTTP status codes instead of generic `500` errors.