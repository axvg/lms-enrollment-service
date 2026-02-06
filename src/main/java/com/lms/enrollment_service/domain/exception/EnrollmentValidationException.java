package com.lms.enrollment_service.domain.exception;

public class EnrollmentValidationException extends RuntimeException {
    public EnrollmentValidationException(String message) {
        super(message);
    }
}
