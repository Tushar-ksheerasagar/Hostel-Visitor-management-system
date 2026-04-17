package com.ooad.hostelvisitor.config;

import com.ooad.hostelvisitor.model.SecurityGuard;
import com.ooad.hostelvisitor.model.Student;
import com.ooad.hostelvisitor.repository.SecurityGuardRepository;
import com.ooad.hostelvisitor.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final SecurityGuardRepository guardRepository;

    public DataSeeder(StudentRepository studentRepository, SecurityGuardRepository guardRepository) {
        this.studentRepository = studentRepository;
        this.guardRepository = guardRepository;
    }

    @Override
    public void run(String... args) {
        if (studentRepository.count() == 0) {
            Student s1 = new Student();
            s1.setName("Aarav Jain");
            s1.setRoomNo("A-101");
            s1.setEmail("aarav@hostel.edu");

            Student s2 = new Student();
            s2.setName("Isha Rao");
            s2.setRoomNo("B-204");
            s2.setEmail("isha@hostel.edu");

            studentRepository.save(s1);
            studentRepository.save(s2);
        }

        if (guardRepository.count() == 0) {
            SecurityGuard g1 = new SecurityGuard();
            g1.setName("Ramesh");
            g1.setBadgeNo("G-1001");

            SecurityGuard g2 = new SecurityGuard();
            g2.setName("Thomas");
            g2.setBadgeNo("G-1002");

            guardRepository.save(g1);
            guardRepository.save(g2);
        }
    }
}
