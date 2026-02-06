package com.lms.enrollment_service.infrastructure.persistence.repository;

import com.lms.enrollment_service.infrastructure.persistence.entity.EnrollmentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    List<EnrollmentEntity> findByUserId(Long userId);
}
