package com.lms.enrollment_service.infrastructure.client.dto;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        String status
) {}
