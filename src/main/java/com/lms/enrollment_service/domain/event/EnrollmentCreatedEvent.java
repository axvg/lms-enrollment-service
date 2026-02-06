package com.lms.enrollment_service.domain.event;

public record EnrollmentCreatedEvent(
        Long enrollmentId,
        Long userId,
        Long courseId,
        String status
) {
}
