package com.ooad.hostelvisitor.service.observer;

import com.ooad.hostelvisitor.model.Visit;

public interface NotificationObserver {
    void onVisitRequested(Visit visit);
}
