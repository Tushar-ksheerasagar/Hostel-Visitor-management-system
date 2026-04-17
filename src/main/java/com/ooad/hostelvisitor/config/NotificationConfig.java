package com.ooad.hostelvisitor.config;

import com.ooad.hostelvisitor.service.observer.StudentEmailNotifier;
import com.ooad.hostelvisitor.service.observer.VisitNotificationPublisher;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class NotificationConfig {

    private final VisitNotificationPublisher publisher;
    private final StudentEmailNotifier studentEmailNotifier;

    public NotificationConfig(VisitNotificationPublisher publisher, StudentEmailNotifier studentEmailNotifier) {
        this.publisher = publisher;
        this.studentEmailNotifier = studentEmailNotifier;
    }

    @PostConstruct
    public void wireObservers() {
        publisher.subscribe(studentEmailNotifier);
    }
}
