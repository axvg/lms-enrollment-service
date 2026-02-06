package com.lms.enrollment_service.domain.model;

import com.lms.enrollment_service.domain.model.Enrollment.Status;
import java.time.LocalDateTime;

public record Enrollment(
        Long id,
        Long userId,
        Long courseId,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static Enrollment create(Long userId, Long courseId) {
        LocalDateTime now = LocalDateTime.now();
        return new Enrollment(null, userId, courseId, Status.PENDING_PAYMENT, now, now);
    }

    public static Enrollment fromPersistence(
            Long id,
            Long userId,
            Long courseId,
            Status status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new Enrollment(id, userId, courseId, status, createdAt, updatedAt);
    }

    public Enrollment confirm() {
        return withStatus(Status.CONFIRMED);
    }

    public Enrollment cancel() {
        return withStatus(Status.CANCELLED);
    }

    public Enrollment markPendingPayment() {
        return withStatus(Status.PENDING_PAYMENT);
    }

    private Enrollment withStatus(Status newStatus) {
        return new Enrollment(id, userId, courseId, newStatus, createdAt, LocalDateTime.now());
    }

    public enum Status {
        PENDING_PAYMENT,
        CONFIRMED,
        CANCELLED
    }
}
