# Todo Application

A RESTful todo application built with Spring Boot, featuring task management with priorities and completion status.

## Features
- Full CRUD operations for todo tasks
- Task prioritization (1-5) with automatic priority labeling
- Task completion status tracking
- Filter tasks by completion status
- Automatic timestamps (createdAt, updatedAt)
- RESTful API endpoints
- In-memory H2 database
- Input validation

## Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 ***REMOVED***
- Maven
- Lombok
- Hibernate Validator

## Setup
1. Clone the repository
2. Build the project: `mvn clean install`
3. Run the application: `mvn spring-boot:run`
4. Access the API at: `http://localhost:8080/api/tasks`

## API Endpoints

### Task Operations
- `GET /api/tasks` - Get all tasks (ordered by priority)
- `GET /api/tasks?completed=true|false` - Filter tasks by completion status
- `GET /api/tasks/{id}` - Get a specific task
- `POST /api/tasks` - Create a new task
- `PUT /api/tasks/{id}` - Update an existing task
- `DELETE /api/tasks/{id}` - Delete a task

### Partial Updates
- `PATCH /api/tasks/{id}/title` - Update task title
- `PATCH /api/tasks/{id}/description` - Update task description
- `PATCH /api/tasks/{id}/priority` - Update task priority
- `PATCH /api/tasks/{id}/dueDate` - Update task due date
- `PATCH /api/tasks/{id}/completed` - Update task completion status

## Request/Response Example

**Create Task Request**
```json
{
    "title": "Learn Spring Boot",
    "description": "Complete todo application tutorial",
    "priority": 4,
    "dueDate": "2024-12-31T10:00:00"
}
```

**Task Response**
```json
{
    "id": 1,
    "title": "Learn Spring Boot",
    "description": "Complete todo application tutorial",
    "completed": false,
    "priority": 4,
    "priorityLabel": "High",
    "dueDate": "2024-12-31T10:00:00",
    "createdAt": "2025-08-30T02:24:22.752123",
    "updatedAt": "2025-08-30T02:24:22.752123"
}
```

## Project Structure
```
src/main/java/com/todo/todo/
├── config/           # Configuration classes
├── controller/       # REST controllers
│   └── TaskController.java
├── dto/
│   ├── request/      # Request DTOs
│   └── response/     # Response DTOs
├── exception/        # Exception handling
├── model/            # Entity classes
│   └── Task.java
├── repository/       # Data access layer
│   └── TaskRepository.java
├── service/          # Business logic
│   └── TaskService.java
├── validation/       # Validation logic
└── ToDoAppApplication.java
```

## Database
- H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:todo`

## Next Steps
- [ ] Add Spring Security for authentication
- [ ] Implement Thymeleaf frontend
- [ ] Add unit and integration tests
- [ ] Add Docker support
- [ ] Add Swagger/OpenAPI documentation
- [ ] Implement task categories/tags
- Add CI/CD pipeline