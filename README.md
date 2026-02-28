# 💰 Expense Tracker

A full-stack expense management web application built using Spring Boot
and deployed on AWS using Docker, Nginx, and HTTPS.

This project demonstrates backend development, cloud infrastructure
management, containerization, and secure production deployment.

------------------------------------------------------------------------

## 🌍 Live Demo

🔗 https://hemanth-expense.duckdns.org/expenses

------------------------------------------------------------------------

## 📌 Project Overview

Expense Tracker allows users to:

-   Add new expenses
-   Edit existing expenses
-   Delete expenses
-   Filter expenses by month
-   Filter expenses by category
-   View total expense dynamically based on filters

The application is deployed on AWS EC2, connected to AWS RDS MySQL,
containerized with Docker, and secured using HTTPS via Let's Encrypt.

------------------------------------------------------------------------

## 🛠 Tech Stack

### Backend

-   Spring Boot 3
-   Spring Data JPA
-   Hibernate ORM
-   Thymeleaf
-   MySQL

### Cloud & Infrastructure

-   AWS EC2 (Application Hosting)
-   AWS RDS (Managed MySQL Database)
-   Docker (Containerization)
-   Nginx (Reverse Proxy)
-   Let's Encrypt (SSL Certificate)
-   DuckDNS (Dynamic DNS)

------------------------------------------------------------------------

## 🏗 Deployment Architecture

Internet\
↓\
DuckDNS Domain\
↓\
HTTPS (Let's Encrypt SSL)\
↓\
Nginx Reverse Proxy\
↓\
Docker Container\
↓\
Spring Boot Application\
↓\
AWS RDS (MySQL Database)

------------------------------------------------------------------------

## 🔐 Security & Production Setup

-   HTTPS enabled using Let's Encrypt
-   SSL auto-renew configured via Certbot
-   Nginx configured as reverse proxy
-   Docker container auto-restart enabled
-   EC2 Security Groups configured for:
    -   Port 22 (SSH)
    -   Port 80 (HTTP)
    -   Port 443 (HTTPS)
-   RDS restricted via inbound rules
-   Environment-based configuration for database connection

------------------------------------------------------------------------

## 🐳 Docker Setup

### Dockerfile

``` dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/expense-tracker-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
```

### Build Image

    docker build -t expense-tracker .

### Run Container

    docker run -d -p 8080:8080 --name expense-app expense-tracker

------------------------------------------------------------------------

## 🗄 Database Configuration

    spring.datasource.url=jdbc:mysql://<rds-endpoint>:3306/expense_db
    spring.datasource.username=YOUR_USERNAME
    spring.datasource.password=YOUR_PASSWORD
    spring.jpa.hibernate.ddl-auto=update

------------------------------------------------------------------------

## 🖥 Running Locally

1.  Clone the repository:

    ``` bash
    git clone https://github.com/YOUR_USERNAME/Expense-Tracker.git
    cd Expense-Tracker
    ```

2.  Build the application:

    ``` bash
    ./mvnw clean package -DskipTests
    ```

3.  Run the application:

    ``` bash
    java -jar target/expense-tracker-0.0.1-SNAPSHOT.jar
    ```

4.  Open in browser:

        http://localhost:8080/expenses

------------------------------------------------------------------------

## 📚 Key Learnings From This Project

-   End-to-end backend development
-   Cloud deployment on AWS
-   Docker containerization
-   Reverse proxy configuration using Nginx
-   SSL certificate installation and renewal
-   Security group configuration
-   Linux server management
-   Production debugging and troubleshooting

------------------------------------------------------------------------

## 🚀 Future Improvements

-   Add authentication (Spring Security)
-   Multi-user support
-   Dashboard with charts (Chart.js)
-   CI/CD pipeline using GitHub Actions
-   Docker Compose configuration
-   Move to ECS or Kubernetes

------------------------------------------------------------------------

## 👨‍💻 Author

Hemanth Kumar Kuppan\
Software Engineer \| Backend & Cloud Enthusiast
