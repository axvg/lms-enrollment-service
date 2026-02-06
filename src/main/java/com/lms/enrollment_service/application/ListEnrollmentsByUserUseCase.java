package com.lms.enrollment_service.application;

import com.lms.enrollment_service.domain.model.Enrollment;
import com.lms.enrollment_service.domain.port.EnrollmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ListEnrollmentsByUserUseCase {
    private final EnrollmentRepository repository;

    public ListEnrollmentsByUserUseCase(EnrollmentRepository repository) {
        this.repository = repository;
    }

    public List<Enrollment> list(Long userId) {
        return repository.findByUserId(userId);
    }
}
