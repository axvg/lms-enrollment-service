package com.lms.enrollment_service.domain.event;

import java.time.Instant;

public record EventMetadata(
        String eventType,
        Instant occurredAt
) {
    public static EventMetadata of(String eventType) {
        return new EventMetadata(eventType, Instant.now());
    }
}
