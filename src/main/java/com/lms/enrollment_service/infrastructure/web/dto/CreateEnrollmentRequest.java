package com.lms.enrollment_service.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;

public record CreateEnrollmentRequest(
        @NotNull Long userId,
        @NotNull Long courseId
) {}
