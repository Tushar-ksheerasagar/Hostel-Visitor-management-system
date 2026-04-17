CREATE DATABASE IF NOT EXISTS hostel_visitor_db;
USE hostel_visitor_db;

CREATE TABLE IF NOT EXISTS students (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    room_no VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS visitors (
    visitor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    purpose VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS security_guards (
    guard_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    badge_no VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS visits (
    visit_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_number VARCHAR(50),
    id_type VARCHAR(20),
    status VARCHAR(40),
    requested_at DATETIME,
    entry_time DATETIME,
    exit_time DATETIME,
    visitor_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    guard_id BIGINT NOT NULL,
    CONSTRAINT fk_visit_visitor FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id),
    CONSTRAINT fk_visit_student FOREIGN KEY (student_id) REFERENCES students(student_id),
    CONSTRAINT fk_visit_guard FOREIGN KEY (guard_id) REFERENCES security_guards(guard_id)
);

INSERT INTO students (name, room_no, email)
SELECT * FROM (
    SELECT 'Aarav Jain', 'A-101', 'aarav@hostel.edu'
) AS temp
WHERE NOT EXISTS (SELECT 1 FROM students WHERE room_no = 'A-101') LIMIT 1;

INSERT INTO students (name, room_no, email)
SELECT * FROM (
    SELECT 'Isha Rao', 'B-204', 'isha@hostel.edu'
) AS temp
WHERE NOT EXISTS (SELECT 1 FROM students WHERE room_no = 'B-204') LIMIT 1;

INSERT INTO security_guards (name, badge_no)
SELECT * FROM (
    SELECT 'Ramesh', 'G-1001'
) AS temp
WHERE NOT EXISTS (SELECT 1 FROM security_guards WHERE badge_no = 'G-1001') LIMIT 1;

INSERT INTO security_guards (name, badge_no)
SELECT * FROM (
    SELECT 'Thomas', 'G-1002'
) AS temp
WHERE NOT EXISTS (SELECT 1 FROM security_guards WHERE badge_no = 'G-1002') LIMIT 1;
