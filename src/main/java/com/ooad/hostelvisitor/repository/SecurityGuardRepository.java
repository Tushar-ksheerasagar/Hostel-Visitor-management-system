package com.ooad.hostelvisitor.repository;

import com.ooad.hostelvisitor.model.SecurityGuard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityGuardRepository extends JpaRepository<SecurityGuard, Long> {
}
