package com.lms.enrollment_service.domain.port;

import com.lms.enrollment_service.domain.event.EventMetadata;
import com.lms.enrollment_service.domain.event.EnrollmentCreatedEvent;
import com.lms.enrollment_service.domain.event.EnrollmentUpdatedEvent;

public interface EnrollmentEventPublisher {
    void publishCreated(EnrollmentCreatedEvent event, EventMetadata metadata);
    void publishUpdated(EnrollmentUpdatedEvent event, EventMetadata metadata);
}
