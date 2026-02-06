package com.lms.enrollment_service.domain.service;

import com.lms.enrollment_service.domain.event.EnrollmentUpdatedEvent;
import com.lms.enrollment_service.domain.model.Enrollment;

public final class EnrollmentDomainService {

    private EnrollmentDomainService() {}

    public static Enrollment handlePayment(Enrollment enrollment, String eventType) {
        return switch (eventType) {
            case "PaymentApprovedEvent" -> enrollment.confirm();
            case "PaymentRejectedEvent" -> enrollment.cancel();
            default -> enrollment;
        };
    }

    public static EnrollmentUpdatedEvent toUpdatedEvent(Enrollment enrollment) {
        return new EnrollmentUpdatedEvent(enrollment.id(), enrollment.status().name());
    }
}
