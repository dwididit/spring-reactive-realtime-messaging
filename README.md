# Spring Reactive Realtime Messaging

This project is a Spring Boot application that demonstrates a reactive real-time messaging system using Spring WebFlux and MongoDB.

## Features

- **Reactive Programming**: Utilizes Spring WebFlux for non-blocking reactive programming.
- **MongoDB**: Uses MongoDB as the database for storing messages.
- **Real-time Messaging**: Supports real-time message streaming using Server-Sent Events (SSE).
- **Logging**: Configured with SLF4J and Logback for logging.

## Prerequisites

- Java 21
- Maven
- MongoDB

## Getting Started

### Clone the Repository

```bash
gh repo clone dwididit/spring-reactive-realtime-messaging
cd spring-reactive-realtime-messaging/
```

### Configuration
Update the MongoDB URI in `src/main/resources/application.yml` if necessary:
```bash
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/chat_reactive
```

## Build and Run
```bash
./mvnw clean install
./mvnw spring-boot:run
```
The application will start on port 8090.