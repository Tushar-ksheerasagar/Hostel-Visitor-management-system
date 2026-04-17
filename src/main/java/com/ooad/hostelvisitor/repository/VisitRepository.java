package com.ooad.hostelvisitor.repository;

import com.ooad.hostelvisitor.model.Visit;
import com.ooad.hostelvisitor.model.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByStatus(VisitStatus status);
}
