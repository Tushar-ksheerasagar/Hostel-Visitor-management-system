package com.ooad.hostelvisitor.service.observer;

import com.ooad.hostelvisitor.model.Visit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VisitNotificationPublisher {

    private final List<NotificationObserver> observers = new ArrayList<>();

    public void subscribe(NotificationObserver observer) {
        observers.add(observer);
    }

    public void notifyVisitRequested(Visit visit) {
        observers.forEach(observer -> observer.onVisitRequested(visit));
    }
}
