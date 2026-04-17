# Hostel Visitor Management System - Project Report

## 1. Problem Statement

Hostel visitor handling in many institutions is still manual and register-based. This causes delayed approvals, poor traceability, and security gaps.

### Existing Problems

- Manual registers are error-prone and difficult to search.
- Student approval is not real-time.
- Security guard decisions are inconsistent without structured verification.
- Entry and exit history is not easily auditable.

### Objective

Build a secure and traceable web-based Hostel Visitor Management System that digitizes request, verification, approval, entry, and exit operations.

## 2. Functional Requirements

### Major Features

1. Register visitor and create request.
2. Verify visitor ID.
3. Student approval/rejection.
4. Track entry and exit.

### Minor Features

1. View complete visitor logs.
2. Generate summary reports.
3. Trigger student notification on valid request.
4. Reject invalid IDs and rejected requests with proper status tracking.

## 3. Non-Functional Requirements

- Security: Strict ID format validation and state-controlled transitions.
- Performance: Simple operations and indexed PK/FK relations via JPA mappings.
- Reliability: Persistent storage in MySQL and transactional service methods.
- Usability: Role-oriented UI pages for request, approval, entry, exit, logs, reports.

## 4. MVC Architecture

- Model: [Student](../src/main/java/com/ooad/hostelvisitor/model/Student.java), [Visitor](../src/main/java/com/ooad/hostelvisitor/model/Visitor.java), [Visit](../src/main/java/com/ooad/hostelvisitor/model/Visit.java), [SecurityGuard](../src/main/java/com/ooad/hostelvisitor/model/SecurityGuard.java)
- View: Thymeleaf templates in [src/main/resources/templates](../src/main/resources/templates)
- Controller: [WebController](../src/main/java/com/ooad/hostelvisitor/controller/WebController.java)
- Service/Business Logic: [HostelSystemService](../src/main/java/com/ooad/hostelvisitor/service/HostelSystemService.java)

## 5. Design Patterns

### 5.1 Creational - Factory

- [VisitFactory](../src/main/java/com/ooad/hostelvisitor/service/factory/VisitFactory.java): Central creation of Visit objects.
- [IdentityVerifierFactory](../src/main/java/com/ooad/hostelvisitor/service/factory/IdentityVerifierFactory.java): Builds the appropriate ID verifier based on ID type.

### 5.2 Structural - Adapter

- [AadhaarVerifierAdapter](../src/main/java/com/ooad/hostelvisitor/service/verification/AadhaarVerifierAdapter.java)
- [PassportVerifierAdapter](../src/main/java/com/ooad/hostelvisitor/service/verification/PassportVerifierAdapter.java)
  These adapt different external verification interfaces to one common IdentityVerifier contract.

### 5.3 Behavioral - Observer

- [VisitNotificationPublisher](../src/main/java/com/ooad/hostelvisitor/service/observer/VisitNotificationPublisher.java)
- [StudentEmailNotifier](../src/main/java/com/ooad/hostelvisitor/service/observer/StudentEmailNotifier.java)
  When a valid request is created, the publisher notifies subscribers without tightly coupling request processing with notification logic.

### 5.4 Singleton

- [HostelSystemRegistry](../src/main/java/com/ooad/hostelvisitor/service/HostelSystemRegistry.java)
  Provides single-instance metric tracking for request/approval/entry/exit counters.

## 6. Design Principles Applied

1. SRP: Verification, persistence, notification, and web routing are separated into dedicated classes.
2. OCP: New ID types can be added by introducing a new adapter and extending factory logic.
3. DIP: Service depends on abstractions like IdentityVerifier and repository interfaces.
4. LSP: Any IdentityVerifier implementation can replace another without changing caller logic.

## 7. Database Design

Database script: [schema.sql](../schema.sql)

### Tables

- students(student_id PK)
- visitors(visitor_id PK)
- security_guards(guard_id PK)
- visits(visit_id PK, visitor_id FK, student_id FK, guard_id FK)

### Relationships

- One Student -> many Visit
- One Visitor -> many Visit
- One SecurityGuard -> many Visit

## 8. Implementation Summary

### Use Cases Covered

- Request visit: /request
- Approve/reject by student: /approvals
- Allow entry: /entry
- Record exit: /exit
- Logs and reports: /logs, /reports

### State Handling

Visit status transitions are represented through enum states:
REQUEST_SENT -> VERIFIED -> APPROVED -> INSIDE_HOSTEL -> EXIT_RECORDED
Alternative rejection states:
REJECTED_INVALID_ID, REJECTED_BY_STUDENT

## 9. Individual Contribution Plan (Sample for 4 Members)

1. Member A: Visitor Registration + Invalid ID rejection handling
2. Member B: Student Approval/Rejection + Notifications
3. Member C: Entry/Exit tracking
4. Member D: Logs/Reports and DB integration

## 10. Demo Checklist

- Show all four UML diagrams (already done)
- Run web app and perform end-to-end flow
- Show each design pattern class in code
- Explain design principles and state transitions
- Show logs and reports pages
