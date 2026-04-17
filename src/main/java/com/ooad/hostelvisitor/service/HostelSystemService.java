package com.ooad.hostelvisitor.service;

import com.ooad.hostelvisitor.dto.VisitRequestForm;
import com.ooad.hostelvisitor.model.SecurityGuard;
import com.ooad.hostelvisitor.model.Student;
import com.ooad.hostelvisitor.model.Visit;
import com.ooad.hostelvisitor.model.VisitStatus;
import com.ooad.hostelvisitor.model.Visitor;
import com.ooad.hostelvisitor.repository.SecurityGuardRepository;
import com.ooad.hostelvisitor.repository.StudentRepository;
import com.ooad.hostelvisitor.repository.VisitRepository;
import com.ooad.hostelvisitor.repository.VisitorRepository;
import com.ooad.hostelvisitor.service.factory.IdentityVerifierFactory;
import com.ooad.hostelvisitor.service.factory.VisitFactory;
import com.ooad.hostelvisitor.service.observer.VisitNotificationPublisher;
import com.ooad.hostelvisitor.service.verification.IdentityVerifier;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HostelSystemService {

    private final VisitorRepository visitorRepository;
    private final StudentRepository studentRepository;
    private final SecurityGuardRepository guardRepository;
    private final VisitRepository visitRepository;
    private final IdentityVerifierFactory identityVerifierFactory;
    private final VisitFactory visitFactory;
    private final VisitNotificationPublisher notificationPublisher;

    public HostelSystemService(
            VisitorRepository visitorRepository,
            StudentRepository studentRepository,
            SecurityGuardRepository guardRepository,
            VisitRepository visitRepository,
            IdentityVerifierFactory identityVerifierFactory,
            VisitFactory visitFactory,
            VisitNotificationPublisher notificationPublisher
    ) {
        this.visitorRepository = visitorRepository;
        this.studentRepository = studentRepository;
        this.guardRepository = guardRepository;
        this.visitRepository = visitRepository;
        this.identityVerifierFactory = identityVerifierFactory;
        this.visitFactory = visitFactory;
        this.notificationPublisher = notificationPublisher;
    }

    @Transactional
    public Visit registerVisitRequest(VisitRequestForm form) {
        Student student = studentRepository.findByRoomNo(form.getStudentRoomNo())
                .orElseThrow(() -> new IllegalArgumentException("No student found for room " + form.getStudentRoomNo()));

        SecurityGuard guard = guardRepository.findById(form.getGuardId())
                .orElseThrow(() -> new IllegalArgumentException("No guard found with id " + form.getGuardId()));

        Visitor visitor = visitorRepository.findByPhone(form.getVisitorPhone())
                .map(existing -> {
                    existing.setName(form.getVisitorName());
                    existing.setPurpose(form.getPurpose());
                    return existing;
                })
                .orElseGet(() -> {
                    Visitor newVisitor = new Visitor();
                    newVisitor.setName(form.getVisitorName());
                    newVisitor.setPhone(form.getVisitorPhone());
                    newVisitor.setPurpose(form.getPurpose());
                    return newVisitor;
                });

        visitor = visitorRepository.save(visitor);

        IdentityVerifier identityVerifier = identityVerifierFactory.build(form.getIdType());
        boolean valid = identityVerifier.verify(form.getIdNumber());

        Visit visit = visitFactory.createInitialVisit(
                visitor,
                student,
                guard,
                form.getIdType(),
                form.getIdNumber(),
                valid ? VisitStatus.VERIFIED : VisitStatus.REJECTED_INVALID_ID
        );

        visit = visitRepository.save(visit);
        HostelSystemRegistry.getInstance().increment("visit.requested");

        if (valid) {
            notificationPublisher.notifyVisitRequested(visit);
        }

        return visit;
    }

    @Transactional
    public Visit approveVisit(Long visitId) {
        Visit visit = getVisitById(visitId);
        if (visit.getStatus() != VisitStatus.VERIFIED) {
            throw new IllegalStateException("Visit is not in VERIFIED state.");
        }
        visit.setStatus(VisitStatus.APPROVED);
        HostelSystemRegistry.getInstance().increment("visit.approved");
        return visitRepository.save(visit);
    }

    @Transactional
    public Visit rejectVisitByStudent(Long visitId) {
        Visit visit = getVisitById(visitId);
        if (visit.getStatus() != VisitStatus.VERIFIED) {
            throw new IllegalStateException("Visit is not in VERIFIED state.");
        }
        visit.setStatus(VisitStatus.REJECTED_BY_STUDENT);
        HostelSystemRegistry.getInstance().increment("visit.rejected.student");
        return visitRepository.save(visit);
    }

    @Transactional
    public Visit allowEntry(Long visitId) {
        Visit visit = getVisitById(visitId);
        if (visit.getStatus() != VisitStatus.APPROVED) {
            throw new IllegalStateException("Only APPROVED visits can enter.");
        }
        visit.setStatus(VisitStatus.INSIDE_HOSTEL);
        visit.setEntryTime(LocalDateTime.now());
        HostelSystemRegistry.getInstance().increment("visit.entry.allowed");
        return visitRepository.save(visit);
    }

    @Transactional
    public Visit recordExit(Long visitId) {
        Visit visit = getVisitById(visitId);
        if (visit.getStatus() != VisitStatus.INSIDE_HOSTEL) {
            throw new IllegalStateException("Visitor is not inside hostel.");
        }
        visit.setStatus(VisitStatus.EXIT_RECORDED);
        visit.setExitTime(LocalDateTime.now());
        HostelSystemRegistry.getInstance().increment("visit.exit.recorded");
        return visitRepository.save(visit);
    }

    public List<Visit> listAllVisits() {
        return visitRepository.findAll();
    }

    public List<Visit> listVisitsByStatus(VisitStatus status) {
        return visitRepository.findByStatus(status);
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public List<SecurityGuard> listGuards() {
        return guardRepository.findAll();
    }

    public long metric(String key) {
        return HostelSystemRegistry.getInstance().getCount(key);
    }

    private Visit getVisitById(Long visitId) {
        return visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found: " + visitId));
    }
}
