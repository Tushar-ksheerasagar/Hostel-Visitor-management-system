package com.ooad.hostelvisitor.repository;

import com.ooad.hostelvisitor.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    Optional<Visitor> findByPhone(String phone);
}
