# B3 Portfolio Manager
## [Itaú Tech Challenge - JR Engineer](./src/main/resources/static/Itau%20Challenge.pdf)

![JAVA_BADGE](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![SPRING_BADGE](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MYSQL_BADGE](https://img.shields.io/badge/MySQL-%2300618a.svg?style=for-the-badge&logo=mysql&logoColor=white)
![DOCKER_BADGE](https://img.shields.io/badge/Docker-%231d63ed.svg?style=for-the-badge&logo=docker&logoColor=white)

This project is an investment portfolio management API, built with Java 21 and Spring Boot 3.5. The API allows users to manage their financial assets, record transactions, and track their investment performance.

---

## 🎯 Questions and Answers

### 1. SQL Table Creation Script

The full SQL script for creating the database and tables can be found in the [schema.sql](src/main/resources/schema.sql) file.

### 2. Justification of Data Types

- `BIGINT` for IDs ensures a large range of unique identifiers for users, assets, operations, and positions.  
- `VARCHAR(255)` for names and emails allows flexibility for varying lengths of text fields.  
- `DECIMAL(19,4)` for prices, brokerage fees, and average prices ensures precise financial calculations without rounding errors.  
- `INT` for quantity is sufficient for holding the number of units in operations and positions.  
- `TIMESTAMP` for `date_time` fields allows precise tracking of operations and quotations, supporting real-time updates.

### 3. Indexes & Performance

- To efficiently query all operations of a user for a specific asset in the last 30 days, the index can be found in the [schema.sql](src/main/resources/schema.sql) file.

### 3.1. Justification of Index and Trigger

The composite index on `(user_id, asset_id, date_time)` allows the database to efficiently filter operations by user and asset, and quickly retrieve recent operations sorted by `date_time`. This avoids full table scans and significantly improves query performance for time-based lookups.

The trigger on the `quotations` table ensures real-time updates of positions, recalculating P&L and average prices immediately when new quotation data is inserted. This guarantees accurate and up-to-date positions for each user without delaying queries.

### How to apply horizontal auto-scaling?
- Verify that the application is stateless to allow scaling across multiple replicas.
- Optimize health checks (readiness and liveness probes) to ensure Kubernetes can properly detect failures and unhealthy instances.
- Containerization (already done).
- Configure resource limits (CPU and memory requests/limits) so that the Horizontal Pod Autoscaler (HPA) can scale accurately.
- Implement better metrics for observability:
  - `portfolio_calculations_total` → track how many portfolio calculations are performed.
  - `portfolio_calculation_duration` → measure calculation latency.
  - `database_connections_active` → monitor active DB connections.
- System metrics such as CPU usage, memory usage, request throughput, and error rates.
- Integrate Prometheus and Grafana for metrics collection, visualization, and alerting.
- Database connection pooling optimization (e.g., tune HikariCP max pool size, idle connections, and timeouts to avoid bottlenecks).
- Graceful shutdown to allow ongoing requests to complete before terminating a pod, preventing request loss during scaling or redeployments.
- Load balancer configuration to distribute traffic evenly across pods and ensure high availability.
- Set up automated deployments (CI/CD pipeline).
- Define alerting rules (e.g., error rate spikes, high latency, DB connection exhaustion).
- Run load and stress tests to validate scaling behavior.
- Continuous monitoring to detect anomalies and adjust scaling policies.

### Comparison between round robin and latency

Round Robin

Distributes requests sequentially among the available servers.  
- Does not consider actual server load or capacity.  
- Issues with sessions unless sticky sessions are used.  
- Inefficient with heterogeneous workloads (fast vs. slow requests).  
- Can create hot spots if a server slows down.  

Latency

Routes requests to the server with the lowest current response time.  
- Higher computational complexity and performance overhead.  
- Risk of instability (thrashing) under variable load.  
- Strong dependency on accurate metrics, subject to lag or misleading spikes.  
- Cold start issue: new servers may appear faster and attract too much traffic.  

## 🚀 Features

- Portfolio Management: Specialized endpoints for portfolio analysis and client management.
- Real-time Calculation: On-demand calculation of the user's consolidated Position, reflecting quantity, average price, and total value based on current quotations.
- API Documentation: Interactive API exploration and documentation provided by Swagger UI (OpenAPI).
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

- [Java](https://docs.oracle.com/en/java/javase/21/) for backend development  
- [Spring Boot](https://docs.spring.io/spring-boot/index.html) framework for building APIs  
- [MySQL](https://dev.mysql.com/doc/) for database storage
- [Docker](https://docs.docker.com/) for containerization  
- [Swagger](https://swagger.io/) for API documentation and testing

## 🧪 Testing

### Run Backend Tests
```bash
./mvnw test
```

## 💻 Usage

After the API is running, you can use the Swagger UI to interact with the available endpoints for portfolio analysis, client queries, and asset quotations. The API can be accessed at `http://localhost:$PORT/swagger/index.html`.

Default $PORT if not provided=8080.

Mock data for testing the application (for session 3 of the challenge) endpoints is provided in the [schema.sql](src/main/resources/schema.sql) file. To use session 3 of the challenge, run the application and test the methods via the terminal.