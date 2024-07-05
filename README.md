# Task Management Application

This Task Management Application is a comprehensive solution developed using Java Spring Boot. It features robust user authentication, session management, and CRUD operations, all backed by secure and scalable technologies. Below is a detailed description of the application, which can be used for your README file on GitHub.

## Overview

The Task Management Application is designed to streamline task management processes with the following key features:

- **User Authentication with JWT**: Secure token-based authentication for login and logout.
- **Session Management with Redis**: Efficient and scalable session storage for token validation and expiry.
- **Admin-Only User Creation**: Role-based authorization allowing only admins to create new users.
- **RESTful API for Task Management**: Comprehensive endpoints for creating, reading, updating, and deleting tasks, all secured using JWT tokens.
- **MongoDB for Data Storage**: Flexible and scalable NoSQL database using MongoDB Atlas.
- **Automated Email Notifications**: Email notifications using JavaMailSender and scheduled cron jobs.

## Documentation

For detailed API documentation, please visit the [Postman documentation](https://documenter.getpostman.com/view/2sA3XV7Jdu?version=latest#b2ae2a55-45ed-42a3-990d-d8a8421eeaa2).

## Features

#### 1. User Authentication with JWT
- Implements secure token-based authentication.
- Supports login and logout functionalities.

#### 2. Session Management with Redis
- Uses Redis for efficient and scalable session storage.
- Manages token validation and expiry.

#### 3. Admin-Only User Creation
- Role-based access control ensures that only admins can create new users.
- Enhances security and user management.

#### 4. RESTful API for Task Management
- Provides endpoints for creating, reading, updating, and deleting tasks.
- All endpoints are secured using JWT tokens to ensure data security.

#### 5. MongoDB for Data Storage
- Utilizes MongoDB Atlas for flexible and scalable NoSQL data storage.
- Ensures high availability and performance.

#### 6. Automated Email Notifications
- Integrates JavaMailSender for sending automated email notifications.
- Uses scheduled cron jobs for timely email delivery as per task due date.
- Automatic email will be send to the users having task assigned and if task is not completed.

## Technologies Used

- **Java**
- **Spring Boot**
- **Spring Java Mail Sender**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Redis**
- **MongoDB**

## Prerequisites

- **Java 17 or higher**
- **Gradle**
- **MongoDB**
- **Redis**
- **Any IDE**

## Contribution

If you would like to contribute to the project, please fork it. I will send you the `gradle.properties` file. You can also reach out to me via email at navishsingh242892@gmail.com.

## Getting Started

To get started with the project, follow these steps:

1. Clone the repository.
2. Set up the necessary environment variables and configuration files.
3. Install the required dependencies using Gradle.
4. Run the application using your preferred IDE or command line.

By following the guidelines above, you will be able to set up and run the Task Management Application seamlessly. 

For any issues or queries, feel free to contact me at navishsingh242892@gmail.com.

---
