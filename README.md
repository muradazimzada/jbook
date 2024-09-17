# JBook App

## Overview
JBook App is a library management system where users can:
- Register and login.
- View all available books.
- Search for books by title, author, or ISBN.
- Create their own books and manage (edit/delete) only the books they have created.

The app is built using microservices architecture and consists of:
- **Book Service**: Manages book-related operations.
- **User Service**: Manages user authentication and profiles.
- **Gateway**: Nginx is used as the reverse proxy to route API calls to backend services.
- **Eureka Server**: Provides service discovery for the microservices.
- **JBook App Web Interface**: Provides interaction with the above APIs, enabling users to perform actions like registering, logging in, viewing, searching, creating, and editing books.

All services are containerized using Docker for easy deployment and scalability.

## Technology Stack
- **Frontend**: Next.js
- **Backend**: Spring Boot (JAVA)
- **Database**: PostgreSQL
- **Service Discovery**: Eureka
- **Gateway**: Nginx (runs on port 8085 and forwards requests to backend services)
- **Containerization**: Docker
- **Authentication**: JWT (JSON Web Token) for secure authentication

## Prerequisites
Before running the JBook App, ensure that you have the following installed:
- **Docker** and **Docker Compose**


## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/muradazimzada/oose-fall24-hw1.git
cd oose-fall24-hw1
```

### 2. Build and Run with Docker
Ensure Docker is running, then use Docker Compose to start all services:

```bash
docker-compose up --build
```

This will bring up all the necessary services: 
- **Web App** (`localhost:8005`)
- **Book Service** (`localhost:8082`)
- **User Service** (`localhost:8081`)
- **Gateway (Nginx)** (`localhost:8085`)
- **Eureka Server** (`localhost:8761`)

### 3. Accessing the Application
Once all the services are running, you can access the application through the Web App at:
```
http://localhost:8005
```

## Option 1: Use Existing Accounts
You can use auto-generated user profiles below the list if you don't want to create new one. These profiles come
with 3 auto-generated books that should be seen on My Books page.
| Email              | Password   |
|--------------------|------------|
| alice@example.com   | password1  |
| bob@example.com     | password2  |
| charlie@example.com | password3  |
| david@example.com   | password4  |
| eva@example.com     | password5  |

## Option 2: Create your Account 
 Enter email and password to register, then use the same credentials to use app's functionalities.


## Useful Commands

- **Stop Docker Compose**:
  ```bash
  docker-compose down
  ```

- **View Logs**:
  ```bash
  docker-compose logs -f
  ```

- **Build Docker Images**:
  ```bash
  docker-compose build
  ```

## Known Issues and Areas for Improvement

- **Error Handling**: 
  The current error handling on both the backend and frontend is basic and could be improved. Future work will include more user-friendly error messages and detailed logging.

- **Registration** 
  The current registration process does not include any validation(except from client side). Future work will include email validation and password strength.

- **Profile Management**:
  Currently, no API exists for updating user profiles. This could be added to allow users to update their account details such as email and password.

- **Deployment**:
  The app is currently not deployed to production. Future enhancements could involve automating the deployment pipeline using CI/CD and deploying to a cloud provider like AWS or Heroku.

## Troubleshooting

If you encounter any issues with running the app or resolving dependencies, try the following:

- Ensure all services are up by checking the Docker logs:
  ```bash
  docker-compose logs
  ```

- If PostgreSQL containers fail to start, check if you have any existing PostgreSQL processes running locally that may be conflicting.

- In case of database connection issues, ensure that the environment variables for the PostgreSQL host, user, and password are correctly set in the `.env` file.

If these suggestions don't solve the issue, feel free to reach out for support.

## Contact
For any unresolved issues or questions, please contact:
- **Murad Azimzada**: [mazimza1@jh.edu](mailto:mazimza1@jh.edu)

