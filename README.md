# Task-Management

This is a Task Management Application developed using Java Spring Boot. The application supports user authentication using JWT (JSON Web Token), session management with Redis and performs CRUD operations with MongoDB.

# Features

1. User Authentication with JWT: Secure token-based authentication (Login/Logout).
2. Session Management with Redis: Efficient and scalable session storage (Token Validation and expiry).
3. Admin-Only User Creation: Only Admins can create new users (Role based authorization).
4. RESTful API for Task Management: Endpoints for creating, reading, updating and deleting tasks (all endpoints secured using JWT token).
5. MongoDB for Data Storage: Flexible and scalable NoSQL database (Using MongoDB Atlas).
6. Automated Email Notifications: Send emails using JavaMailSender and scheduled cron jobs.

# Technologies Used

Java
Spring Boot
Spring Security
JWT (JSON Web Token)
Redis
MongoDB

# Prerequisites

Java 17 or higher
Gradle
MongoDB
Redis
