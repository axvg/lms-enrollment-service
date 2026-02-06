package com.lms.enrollment_service.domain.event;

public record EnrollmentUpdatedEvent(
        Long enrollmentId,
        String status
) {
}
