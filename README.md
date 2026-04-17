# Hostel Visitor Management System (OOAD Mini Project)

This project implements a complete Hostel Visitor Management System using Java Spring Boot with MVC architecture.

## Tech Stack

- Java 17
- Spring Boot 3 (Web + Thymeleaf + Spring Data JPA)
- MySQL
- Maven

## Implemented Major Features

1. Visitor registration and request creation
2. ID verification (Aadhaar/Passport)
3. Student approval or rejection flow
4. Entry and exit tracking

## Implemented Minor Features

1. Visitor logs view
2. Report dashboard
3. Notification trigger when valid request is created
4. Invalid ID and student rejection handling

## Run Steps

1. Create MySQL DB and tables using [schema.sql](schema.sql)
2. Update DB credentials in [src/main/resources/application.properties](src/main/resources/application.properties)
3. Run:
   mvn spring-boot:run
4. Open:
   http://localhost:8080

## Screens to Capture for Submission

- Visitor registration form: /request
- Student approval page: /approvals
- Visitor logs: /logs
- Reports page: /reports

## Design Patterns Included

- Creational: Factory ([VisitFactory](src/main/java/com/ooad/hostelvisitor/service/factory/VisitFactory.java), [IdentityVerifierFactory](src/main/java/com/ooad/hostelvisitor/service/factory/IdentityVerifierFactory.java))
- Structural: Adapter ([AadhaarVerifierAdapter](src/main/java/com/ooad/hostelvisitor/service/verification/AadhaarVerifierAdapter.java), [PassportVerifierAdapter](src/main/java/com/ooad/hostelvisitor/service/verification/PassportVerifierAdapter.java))
- Behavioral: Observer ([VisitNotificationPublisher](src/main/java/com/ooad/hostelvisitor/service/observer/VisitNotificationPublisher.java), [StudentEmailNotifier](src/main/java/com/ooad/hostelvisitor/service/observer/StudentEmailNotifier.java))
- Singleton: [HostelSystemRegistry](src/main/java/com/ooad/hostelvisitor/service/HostelSystemRegistry.java)

## Project Report

Detailed report is in [docs/PROJECT_REPORT.md](docs/PROJECT_REPORT.md).
