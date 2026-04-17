package com.ooad.hostelvisitor.service.factory;

import com.ooad.hostelvisitor.model.IdType;
import com.ooad.hostelvisitor.model.SecurityGuard;
import com.ooad.hostelvisitor.model.Student;
import com.ooad.hostelvisitor.model.Visit;
import com.ooad.hostelvisitor.model.VisitStatus;
import com.ooad.hostelvisitor.model.Visitor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VisitFactory {

    public Visit createInitialVisit(
            Visitor visitor,
            Student student,
            SecurityGuard guard,
            IdType idType,
            String idNumber,
            VisitStatus status
    ) {
        Visit visit = new Visit();
        visit.setVisitor(visitor);
        visit.setStudent(student);
        visit.setGuard(guard);
        visit.setIdType(idType);
        visit.setIdNumber(idNumber);
        visit.setStatus(status);
        visit.setRequestedAt(LocalDateTime.now());
        return visit;
    }
}
