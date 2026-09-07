Table of Contents
What is DTO?
Why do we need DTO?
Problems without DTO
Advantages of DTO
Types of DTO
Request DTO
Response DTO
Entity vs DTO
Project Flow
Validation in DTO
DTO Conversion
Best Practices
Common Mistakes
Interview Questions
Summary

1. What is DTO?
DTO stands for Data Transfer Object.
A DTO is a simple Java class used to transfer data between different layers of an application or between the client and the server.
A DTO does not represent a database table.
It is only used for sending and receiving data.
Example ->
   React
   ↓
   CategoryRequestDTO
   ↓
   Controller
   ↓
   Service
   ↓
   Entity
   ↓
   Database
2. Why do we need DTO?

Imagine your entity looks like this:
@Entity
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
}
If we directly expose this entity to the client, the client can send:

{
"id":100,
"name":"Krish",
"email":"abc@gmail.com",
"password":"123456",
"role":"ADMIN"
}
Problems:

User can modify ID
User can assign ADMIN role
User can change fields they should not control

DTO solves this problem.

3. DTO solves this problem.
   Without DTO:

Client
↓
Entity
↓
Database

Problems:

No validation
Poor security
Database structure is exposed
Difficult to maintain
Cannot hide sensitive fields
Tight coupling

4. Without DTO:

Client
↓
Entity
↓
Database

Problems:

No validation
Poor security
Database structure is exposed
Difficult to maintain
Cannot hide sensitive fields
Tight coupling

✔ Validation

DTO supports validation.

Example:
``
@NotBlank
private String name;

@Email
private String email;

@Size(min = 8)
private String password;
