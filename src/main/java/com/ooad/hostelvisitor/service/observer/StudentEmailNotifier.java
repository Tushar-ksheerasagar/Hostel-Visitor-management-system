package com.ooad.hostelvisitor.service.observer;

import com.ooad.hostelvisitor.model.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StudentEmailNotifier implements NotificationObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentEmailNotifier.class);

    @Override
    public void onVisitRequested(Visit visit) {
        LOGGER.info(
                "Notification sent to student {} for visitor {} (visitId={})",
                visit.getStudent().getEmail(),
                visit.getVisitor().getName(),
                visit.getVisitId()
        );
    }
}
