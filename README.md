# B3 Portfolio Manager
## [Itaú Tech Challenge - JR Engineer](./src/main/resources/static/Itau%20Challenge.pdf)

![JAVA_BADGE](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![SPRING_BADGE](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MYSQL_BADGE](https://img.shields.io/badge/MySQL-%2300618a.svg?style=for-the-badge&logo=mysql&logoColor=white)
![DOCKER_BADGE](https://img.shields.io/badge/Docker-%231d63ed.svg?style=for-the-badge&logo=docker&logoColor=white)

This project is an investment portfolio management API, built with Java 21 and Spring Boot 3.5. The API allows users to manage their financial assets, record transactions, and track their investment performance.

Key features:

- Asset and User Management: Enables the registration and management of different types of financial assets and platform users.
- Transaction Logging: Allows for recording buy and sell transactions for a portfolio.
- Consolidated Position Inquiry: Calculates and returns the user's updated position, including asset quantity, average price, and total portfolio value based on the latest quotations.
- Quotation Updates: Allows for updating asset prices for accurate portfolio valuation.

The project is designed to be robust, using a database for persistence, and includes API documentation with Swagger/OpenAPI, as well as automated tests to ensure the integrity of the business rules.

---

## 🚀 Features

- Portfolio Management: Full CRUD endpoints for managing Users, Assets, and financial Operations (buy/sell).
- Real-time Calculation: On-demand calculation of the user's consolidated Position, reflecting quantity, average price, and total value based on current quotations.
- Data Validation: Robust validation of business rules for all incoming requests to ensure data integrity.
- API Documentation: Interactive API exploration and documentation provided by Swagger UI (OpenAPI).
- Automated Testing: A comprehensive integration test suite using Rest-Assured to ensure API reliability.
- Monitoring & Health: Observability endpoints powered by Spring Boot Actuator for health checks and application metrics.
- Containerization: Ready to deploy using Docker, with a multi-stage Dockerfile for optimized images.

## 🏗️ Installation

To use this project, you need to follow these steps:

1. Clone the repository: `git clone https://github.com/gustavobarez/b3-portfolio-manager.git`
2. Install the dependencies: `mvn clean package`
3. Run the application: `mvn spring-boot:run`

## ⚙️ Makefile Commands

The project includes a Makefile to help you manage common tasks more easily. Here's a list of the available commands and a brief description of what they do:

- `make run`: Run the application locally
- `make build`: Build the application and package a JAR
- `make test`: Run tests for all packages in the project.
- `make docs`: Generate Swagger API documentation
- `make docker-build`: Build the Docker image for the application
- `make docker-run`: Run the application in a Docker container
- `make clean`: Clean project build artifacts

To use these commands, simply type `make` followed by the desired command in your terminal. For example:

```sh
make run
```

## 🐳 Docker and Docker Compose

This project includes a `Dockerfile` and `docker-compose.yml` file for easy containerization and deployment. Here are the most common Docker and Docker Compose commands you may want to use:

- `docker build -t your-image-name .`: Build a Docker image for the project. Replace `your-image-name` with a name for your image.
- `docker run -p 8080:8080 -e PORT=8080 your-image-name`: Run a container based on the built image. Replace `your-image-name` with the name you used when building the image. You can change the port number if necessary.

If you want to use Docker Compose, follow these commands:

- `docker compose build`: Build the services defined in the `docker-compose.yml` file.
- `docker compose up`: Run the services defined in the `docker-compose.yml` file.

To stop and remove containers, networks, and volumes defined in the `docker-compose.yml` file, run:

```sh
docker-compose down
```

For more information on Docker and Docker Compose, refer to the official documentation:

- [Docker](https://docs.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## 🛠️ Used Tools

This project uses the following tools:

- [Java](https://docs.oracle.com/en/java/javase/17/) for backend development  
- [Spring Boot](https://docs.spring.io/spring-boot/index.html) framework for building APIs  
- [MySQL](https://dev.mysql.com/doc/) for database storage
- [Docker](https://docs.docker.com/) for containerization  
- [Swagger](https://swagger.io/) for API documentation and testing

## 💻 Usage

After the API is running, you can use the Swagger UI to interact with the endpoints for searching, creating, editing, and deleting job opportunities. The API can be accessed at `http://localhost:$PORT/swagger/index.html`.

Default $PORT if not provided=8080.
