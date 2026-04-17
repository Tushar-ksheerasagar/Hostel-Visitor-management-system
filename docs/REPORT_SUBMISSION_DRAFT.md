# Mini Project Report Content (Template-Aligned)

Use this content to fill the provided Word template section by section.

## Cover Page Fields

- Title: Hostel Visitor Management System
- Subject Code and Name: UE23CS352B - Object Oriented Analysis & Design
- Team Members:
  - [Member 1 Name] - [SRN 1]
  - [Member 2 Name] - [SRN 2]
  - [Member 3 Name] - [SRN 3]
  - [Member 4 Name] - [SRN 4]
- Semester - Section: [e.g., 6 - A]
- Faculty Name: [Faculty Name]
- Term: January - May 2026
- Department/University details: Keep as in template

## Problem Statement

Hostel visitor handling is often manual and register-based, causing delays in approvals, inconsistent identity checks, and poor auditability. The objective of this project is to build a web-based Hostel Visitor Management System that digitizes the complete visitor lifecycle: request creation, ID verification, student decision, entry permission, and exit recording. The system improves traceability, security, and operational efficiency through structured status transitions and persistent records.

## Key Features

### Major Features

1. Visitor registration and request creation.
2. ID verification using Aadhaar or Passport.
3. Student approval/rejection workflow.
4. Security guard controlled entry and exit tracking.

### Minor Features

1. Visit logs page for complete history.
2. Reports dashboard with summary counts.
3. Notification trigger when a valid request is created.
4. Invalid ID and student rejection handling with separate statuses.

## Models

1. Student
   - Fields: studentId, name, roomNo, email
   - Represents hostel resident receiving the visit request.

2. Visitor
   - Fields: visitorId, name, phone, purpose
   - Represents person requesting to visit.

3. SecurityGuard
   - Fields: guardId, name, badgeNo
   - Represents gate-level authority handling entry/exit.

4. Visit
   - Fields: visitId, idNumber, idType, status, requestedAt, entryTime, exitTime
   - Relations: many-to-one with Visitor, Student, SecurityGuard
   - Captures complete lifecycle of one visit request.

## Use Case Diagram

Actors:

- Visitor
- Student
- Security Guard

Primary use cases:

1. Submit visit request.
2. Verify visitor identity.
3. Approve/reject request.
4. Allow entry.
5. Record exit.
6. View logs and reports.

(Insert your UML use case diagram image in this section.)

## Class Diagram

Core classes:

- Entity classes: Student, Visitor, SecurityGuard, Visit
- Controller: WebController
- Service: HostelSystemService
- Repositories: StudentRepository, VisitorRepository, SecurityGuardRepository, VisitRepository
- Verification interfaces/classes: IdentityVerifier, AadhaarVerifierAdapter, PassportVerifierAdapter, AadhaarGateway, PassportGateway
- Observer classes: NotificationObserver, VisitNotificationPublisher, StudentEmailNotifier
- Factory classes: VisitFactory, IdentityVerifierFactory
- Singleton utility: HostelSystemRegistry

(Insert your UML class diagram image in this section.)

## State Diagram

Visit status states used in code:

- REQUEST_SENT
- VERIFIED
- REJECTED_INVALID_ID
- APPROVED
- REJECTED_BY_STUDENT
- INSIDE_HOSTEL
- EXIT_RECORDED

Main transition flow:
REQUEST_SENT -> VERIFIED -> APPROVED -> INSIDE_HOSTEL -> EXIT_RECORDED

Rejection alternatives:

- VERIFIED -> REJECTED_BY_STUDENT
- REQUEST_SENT -> REJECTED_INVALID_ID

(Insert your UML state diagram image in this section.)

## Activity Diagrams

Suggested activity diagrams to include:

1. End-to-end visit request to exit flow.
2. Approval/rejection branch flow.
3. Entry/exit validation flow.

(Insert your UML activity diagram images in this section.)

## Design Principles, and Design Patterns

## MVC Architecture used? Yes

- Model: Student, Visitor, SecurityGuard, Visit
- View: Thymeleaf templates (request, approvals, entry, exit, logs, reports)
- Controller: WebController endpoints for each workflow stage
- Service: HostelSystemService for business logic and state transitions

## Design Principles

1. Single Responsibility Principle (SRP)
   - Each component handles one concern.
   - Example: verification adapters only verify IDs; repositories only persist data; controller only handles routing.

2. Open/Closed Principle (OCP)
   - New ID types can be added by creating a new verifier adapter and extending IdentityVerifierFactory.

3. Dependency Inversion Principle (DIP)
   - Service layer depends on abstractions such as IdentityVerifier and repository interfaces.

4. Liskov Substitution Principle (LSP)
   - Any implementation of IdentityVerifier can be used wherever IdentityVerifier is expected.

## Design Patterns

1. Factory Pattern (Creational)
   - VisitFactory creates initialized Visit objects.
   - IdentityVerifierFactory returns correct verifier strategy for Aadhaar/Passport.

2. Adapter Pattern (Structural)
   - AadhaarVerifierAdapter and PassportVerifierAdapter adapt external gateway APIs to the common IdentityVerifier interface.

3. Observer Pattern (Behavioral)
   - VisitNotificationPublisher notifies subscribed observers when a valid visit request is created.
   - StudentEmailNotifier receives and processes notification events.

4. Singleton Pattern
   - HostelSystemRegistry maintains a single shared counter registry for runtime metrics.

## Github link to the Codebase (repository should be public)

- https://github.com/[your-username]/[your-repository-name]

## Screenshots UI

Insert screenshots of the following pages:

1. Visitor input form: /request
2. Student approval screen: /approvals
3. Visitor logs: /logs
4. Reports dashboard: /reports

## Individual contributions of the team members

| Name       | Module worked on                          |
| ---------- | ----------------------------------------- |
| [Member 1] | Visitor Registration + ID Verification    |
| [Member 2] | Student Approval/Rejection + Notification |
| [Member 3] | Entry/Exit Tracking                       |
| [Member 4] | Logs/Reports + Database Integration       |
