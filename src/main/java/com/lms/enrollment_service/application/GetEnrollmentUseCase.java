package com.lms.enrollment_service.application;

import com.lms.enrollment_service.domain.exception.EnrollmentNotFoundException;
import com.lms.enrollment_service.domain.model.Enrollment;
import com.lms.enrollment_service.domain.port.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetEnrollmentUseCase {

    private final EnrollmentRepository repository;

    public GetEnrollmentUseCase(EnrollmentRepository repository) {
        this.repository = repository;
    }

    public Enrollment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
    }
}
