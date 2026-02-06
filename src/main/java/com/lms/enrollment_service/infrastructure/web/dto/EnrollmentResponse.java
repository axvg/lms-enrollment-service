package com.lms.enrollment_service.infrastructure.web.dto;

import com.lms.enrollment_service.domain.model.Enrollment;
import java.time.LocalDateTime;

public record EnrollmentResponse(
        Long id,
        Long userId,
        Long courseId,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static EnrollmentResponse from(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.id(),
                enrollment.userId(),
                enrollment.courseId(),
                enrollment.status().name(),
                enrollment.createdAt(),
                enrollment.updatedAt()
        );
    }
}
