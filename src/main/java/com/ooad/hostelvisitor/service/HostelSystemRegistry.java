package com.ooad.hostelvisitor.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Explicit Singleton used to keep runtime counters for reporting.
public final class HostelSystemRegistry {

    private static final HostelSystemRegistry INSTANCE = new HostelSystemRegistry();

    private final Map<String, Long> counters = new ConcurrentHashMap<>();

    private HostelSystemRegistry() {
    }

    public static HostelSystemRegistry getInstance() {
        return INSTANCE;
    }

    public void increment(String key) {
        counters.merge(key, 1L, Long::sum);
    }

    public long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }
}
