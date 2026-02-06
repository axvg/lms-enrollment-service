package com.lms.enrollment_service.domain.port;

import com.lms.enrollment_service.domain.model.Enrollment;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);
    Optional<Enrollment> findById(Long id);
    List<Enrollment> findByUserId(Long userId);
}
