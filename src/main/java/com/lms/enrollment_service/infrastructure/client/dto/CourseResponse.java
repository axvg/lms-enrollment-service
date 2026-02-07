package com.lms.enrollment_service.infrastructure.client.dto;

public record CourseResponse(
        Long id,
        String title,
        String status
) {}
