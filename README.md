# Full-Stack Financial Tracker

A full-featured financial tracker web app built with **Spring Boot (Java)** on the backend and **React (Vite + JavaScript)** on the frontend. 
This project allows users to manage tasks and projects, track income and expenses, and visualize transactions like a roadmap.

---

## Features

### Backend (Spring Boot)
- RESTful API with CRUD operations
- Entities: `Task`, `Project` (many-to-one relationship)
- DTO usage for clean data transfer
- Real MySQL database integration
- Environment variable support via `.env`
- Error handling and validation

### Frontend (React + Vite)
- Fast, modern React setup using Vite
- Fetch and display tasks and projects from backend API (PLANNED)
- Dynamic form handling for task and project creation (PLANNED)
- Styled components for consistent UI (PLANNED)
- Environment variable support for API URLs (PLANNED)

---

## Technologies Used

| Layer         | Stack                                  |
|---------------|-----------------------------------------|
| Frontend      | React, Vite, JavaScript, CSS           |
| Backend       | Java, Spring Boot, Spring Data JPA     |
| Database      | MySQL                                  |
| Build Tools   | Maven, npm                             |
| Version Control | Git, GitHub                          |
| Dev Tools     | VS Code, IntelliJ IDEA, Postman        |

---

## Endpoints Overview

| Method | Endpoint             | Description                     |
|--------|----------------------|---------------------------------|
| GET    | `/api/tasks`         | Get all tasks                   |
| GET    | `/api/tasks/{id}`    | Get a specific task             |
| POST   | `/api/tasks`         | Create a new task               |
| DELETE | `/api/tasks/{id}`    | Delete a task                   |
| GET    | `/api/projects`      | Get all projects                |
| POST   | `/api/projects`      | Create a new project            |
| GET    | `/api/projects/{id}` | Get project with its tasks      |

> Note: DTOs are used to return only relevant fields from `Task` and `Project` entities.

---

## Getting Started

### Prerequisites

- Java 21+
- Node.js and npm
- MySQL installed and running

---

### Backend Setup

```bash
# Navigate to backend folder
cd backend

# Add .env file
cp .env.example .env

# Run the app
./mvnw spring-boot:
```

---

### Frontend Setup

```bash
# Navigate to frontend folder
cd frontend

# Run the app
npm run dev