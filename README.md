# Quiz Application

## Introduction
The **Quiz Application** is a simple web-based multiple-choice quiz system built using **Spring Boot** for the backend and **HTML/CSS/JavaScript** for the frontend. It supports quiz sessions where users answer pre-defined questions and receive their results.

## Features
- Multiple-choice questions
- Single user session (no authentication required)
- Questions stored in an **H2 in-memory database**
- Users can answer each question only once
- Quiz session automatically ends after all questions are answered
- RESTful API with **Spring Boot**

## Project Structure
```
quiz-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/quizapp/
│   │   │       ├── QuizAppApplication.java
│   │   │       ├── controller/
│   │   │       │   └── QuizController.java
│   │   │       ├── model/
│   │   │       │   ├── Question.java
│   │   │       │   ├── Answer.java
│   │   │       │   ├── QuizSession.java
│   │   │       │   └── UserResponse.java
│   │   │       ├── repository/
│   │   │       │   ├── QuestionRepository.java
│   │   │       │   ├── QuizSessionRepository.java
│   │   │       │   └── UserResponseRepository.java
│   │   │       ├── service/
│   │   │       │   └── QuizService.java
│   │   │       └── dto/
│   │   │           ├── QuizSessionDTO.java
│   │   │           ├── QuestionDTO.java
│   │   │           ├── AnswerSubmissionDTO.java
│   │   │           └── QuizResultDTO.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── data.sql
│   │   │   ├── static/
│   │   │   │   ├── css/style.css
│   │   │   │   └── js/app.js
│   │   │   └── templates/
│   │   │       └── index.html
│   └── test/
│       └── java/
│           └── com/example/quizapp/
│               └── QuizAppApplicationTests.java
├── pom.xml
└── README.md
```

## Application UI 

## Setup Instructions

### Prerequisites
- Java 17 or later
- Maven
- Spring Boot

### Steps to Run the Application
1. Clone the repository:
   ```sh
   git clone https://github.com/AlexAjit/PlayQuiz.git
   cd PlayQuiz
   ```
2. Build the application:
   ```sh
   mvn clean install
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Access the quiz in your browser at:
   ```
   http://localhost:8080/
   ```

### H2 Database Console
Spring Boot provides an in-memory H2 database, which can be accessed at:
```
http://localhost:8080/h2-console
```
Use the following credentials:
```
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (leave empty)
```

## API Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/questions` | Retrieve all quiz questions |
| POST | `/api/submit` | Submit an answer |
| GET | `/api/result` | Get the quiz result |

## Technologies Used
- **Spring Boot** (Backend)
- **H2 Database** (In-Memory Database)
- **Spring Data JPA** (ORM)
- **Thymeleaf** (For rendering HTML pages)
- **HTML, CSS, JavaScript** (Frontend)
---
**By:** Ajit

