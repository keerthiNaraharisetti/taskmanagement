# Task Management API (Spring Boot)

## Overview

Task Management API is built using Spring Boot that provides CRUD (Create, Read, Update, Delete) operations on tasks. It enables users to manage their tasks efficiently, with features such as user authentication, task operations additional functionalities like pagination, sorting, and filtering.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Database](#database)
- [Additional Features](#additional-features)
- [Getting Started](#getting-started)

## Features

- **User Authentication:**
  - Secure user authentication using Spring Security ensures authorized access to task-related functionalities.
  - Admin users have full access to all tasks and user management.
  - Used `BCryptPasswordEncoder` for securely hashing passwords.
   - Endpoint: `POST /login`
   - Authenticate a user and generate an authentication token.
   - Usage Example: Send a POST request to `/login` with a JSON body containing `username` and `password`.
   - Request Body Example (`LoginRequest`):
     ```json
     {
       "username": "example_user",
       "password": "secure_password"
     }
     ```
   - Response Example (`AuthTokenDTO`):
     ```json
     {
       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
     }
     ```
   
   - The authentication token should be included in the Authorization header as Bearer for subsequent requests to access the protected endpoints.


- **Task Operations:**
   1. Retrieve a Single Task (`/tasks/{id}`):
      - Endpoint: `GET /tasks/{id}`
      - Usage Example: `GET /tasks/1`
      - Response Example (`TaskDTO`):
         ```json
         {
            "id": 1,
            "title": "Complete Project Proposal",
            "description": "Draft and submit the project proposal document.",
            "status": "IN_PROGRESS",
            "dueDate": "2024-02-10T12:00:00Z",
            "completionDate": null
         }
         ```

   2. Retrieve Paginated List of Tasks (`/tasks/`):
      - Endpoint: `GET /tasks/`
      - Usage Example: `GET /tasks/?page=0&size=10`
      - Authorization: Requires `ADMIN` role.
      - Response Example (`Page<TaskDTO>`):
         ```json
         {
            "content": [
               {
               "id": 1,
               "title": "Complete Project Proposal",
               "description": "Draft and submit the project proposal document.",
               "status": "IN_PROGRESS",
               "dueDate": "2024-02-10T12:00:00Z",
               "completionDate": null
               },
               // Additional tasks...
            ],
            "totalElements": 25,
            "size": 10,
            "number": 0
         }
         ```

   3. Filter Tasks (`/tasks/filter`):
      - Endpoint: `GET /tasks/filter`
      - Authorization: Requires `ADMIN` role.
      - Usage Example: `GET /tasks/filter?search=status:complete&page=0&size=10`

   4. Create a Task (`/tasks/`):
      - Endpoint: `POST /tasks/`
      - Usage Example: `POST /tasks/` with a JSON body containing task details.
      - Request Body Example (`TaskRequest`):
         ```json
         {
            "title": "New Task",
            "description": "Description of the new task.",
            "status": "PENDING",
            "dueDate": "2024-02-20T15:30:00Z",
            "completionDate": null
         }
         ```

   5. Update a Task (`/tasks/{id}`):
      - Endpoint: `PUT /tasks/{id}`
      - Usage Example: `PUT /tasks/1` with a JSON body containing updated task details.
      - Request Body Example (`TaskRequest`):
         ```json
         {
            "status": "PENDING"
         }
         ```

   6. Delete a Task (`/tasks/{id}`):
      - Endpoint: `DELETE /tasks/{id}`
      - Usage Example: `DELETE /tasks/1`

   7. Retrieve Tasks Created by a User (`/tasks/user/{id}`):
      - Endpoint: `GET /tasks/user/{id}`
      - Authorization: Requires `ADMIN` role.
      - Usage Example: `GET /tasks/user/1`

- **User Operations**:

   1. Retrieve a User by ID (`/users/{id}`):
      - Endpoint: `GET /users/{id}`
      - Usage Example: `GET /users/1`
      - Response Example (`UserDTO`):
         ```json
         {
            "id": 1,
            "username": "Key-admin",
            "name": "Keerthi Naraharisetti"
         }
         ```

   2. Retrieve Paginated List of Users (`/users/`):
      - Endpoint: `GET /users/`
      - Usage Example: `GET /users/?page=0&size=10`

   3. Create a User (`/users/`):
      - Endpoint: `POST /users/`
      - Usage Example: `POST /users/` with a JSON body containing user details.
      - Authorization: Requires `ADMIN` role.
      - Request Body Example (`UserRequest`):
         ```json
         {
            "username": "new_user",
            "password": "secure_password",
            "name": "New User"
         }
         ```
      - Username ended with -admin will be granted admin access

   4. Update a User by ID (`/users/{id}`):
      - Endpoint: `PUT /users/{id}`
      - Usage Example: `PUT /users/1` with a JSON body containing updated user details.

   5. Delete a User by ID (`/users/{id}`):
      - Endpoint: `DELETE /users/{id}`
      - Usage Example: `DELETE /users/1`
      - Authorization: Requires `ADMIN` role.

## Requirements

- [Java 17 or later]
- [Spring Boot 2.x]
- [MySQL Database]
- [Flyway for database migration]
- [Lombok for code simplification]
- [MapStruct for object mapping]
- [Spring Security for user authentication]
- [JWT (JSON Web Token) for secure token-based authentication]

## Database
  - Used a relational database (MySQL) for storing user and task-related information using Spring Data JPA.
  - For Database migrations using Flyway for seamless schema evolution.
  - Configured the database connection in `src/main/resources/application.properties`.


## Additional Features
  - Proper error handling and status codes in API responses.
  - Error Response:
      ```json
      {
      "statusCode": 404,
      "message": "Resource not found."
      }
      ```

## Getting Started

1. Clone the repository: `git clone [repository-url]`
2. Build the project: `./mvnw clean install`
3. Configure the database connection in `src/main/resources/application.properties`.
4. Run the application: `./mvnw spring-boot:run`
