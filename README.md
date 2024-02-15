# Online Certification Website Backend

This repository contains a backend project developed in Java using Spring Boot, Maven, Lombok, PostgreSQL, JDBC, Docker, REST API, and Hibernate ORM.

## Project Overview

This project aims to provide a robust backend solution for an online certification test system, featuring a database setup for questions, answers, certifications, students and a top 10 ranking.

## Technologies Used

- **Java**: Main programming language.
- **Spring Boot**: Framework for creating standalone, production-grade Spring-based applications.
- **Maven**: Build automation tool used for managing dependencies and building the project.
- **Lombok**: Library to reduce boilerplate code in Java.
- **PostgreSQL**: Relational database management system used for data storage.
- **JDBC**: Java Database Connectivity for connecting Java applications to databases.
- **Docker**: Containerization platform used for packaging and running applications.
- **REST API**: Architectural style for designing networked applications.
- **Hibernate ORM**: Object-relational mapping library for simplifying database interactions in Java.

## Configuration

Although the project runs just fine with the instructions mentioned in the next section, you may want to tinker with the project configurations using the following files:

- **Database Configuration**: 
  - The PostgreSQL database connection details can be configured in the `application.properties` and `docker-compose.yml` files.
  
- **REST API Endpoints**:
  - All REST API endpoints are defined in the respective controllers. Check the `controller` packages for more details.

## Setup Instructions

To run this project locally, follow these steps:

1. **Clone the repository**:
   ```
   git clone https://github.com/g-ramalho/certification-nlw-java
   cd certification-nlw-java
   ```

2. **Run the database using Docker**:
   ```
   docker-compose up -d
   ```
   ^ *you might have to try `docker compose up -d` depending on your OS* ^

3. **Build the project**:
   ```
   mvn clean install
   ```

4. **Create the database seed**:

    run `src/main/java/br/com/gramalho/certification_nlw/seed/CreateSeed.java`

5. **Access the application**:
   Once the Docker containers are up and running, you can access the application via [http://localhost:8085](http://localhost:8085) (or whatever you've set up in the `application.properties` file).

