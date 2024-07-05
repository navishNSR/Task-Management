# Task-Management

This is a Task Management Application developed using Java Spring Boot. The application supports user authentication using JWT (JSON Web Token), session management with Redis and performs CRUD operations with MongoDB. It also includes role-based access control for user management, integrates JavaMailSender for automated email notifications and utilizes Spring Boot's RESTful API capabilities for seamless task management.

# Documentation

Link: https://documenter.getpostman.com/view/2sA3XV7Jdu?version=latest#b2ae2a55-45ed-42a3-990d-d8a8421eeaa2

# Features

1. User Authentication with JWT: Secure token-based authentication (Login/Logout).
2. Session Management with Redis: Efficient and scalable session storage (Token Validation and expiry).
3. Admin-Only User Creation: Only Admins can create new users (Role based authorization).
4. RESTful API for Task Management: Endpoints for creating, reading, updating and deleting tasks (all endpoints secured using JWT token).
5. MongoDB for Data Storage: Flexible and scalable NoSQL database (Using MongoDB Atlas).
6. Automated Email Notifications: Send emails using JavaMailSender and scheduled cron jobs.

# Technologies Used

1. Java
2. Spring Boot
3. Spring Java Mail Sender
4. Spring Security
5. JWT (JSON Web Token)
6. Redis
7. MongoDB

# Prerequisites

1. Java 17 or higher
2. Gradle
3. MongoDB
4. Redis
5. Any IDE


**NOTE: If you want to contribute to the project please fork it. I will send you "gradle.properties" file. You can also ping me on my email navishsingh242892@gmail.com**
