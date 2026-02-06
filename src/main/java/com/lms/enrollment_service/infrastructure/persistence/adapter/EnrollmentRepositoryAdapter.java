package com.lms.enrollment_service.infrastructure.persistence.adapter;

import com.lms.enrollment_service.domain.model.Enrollment;
import com.lms.enrollment_service.domain.port.EnrollmentRepository;
import com.lms.enrollment_service.infrastructure.persistence.entity.EnrollmentEntity;
import com.lms.enrollment_service.infrastructure.persistence.repository.JpaEnrollmentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentRepositoryAdapter implements EnrollmentRepository {

    private final JpaEnrollmentRepository repository;

    public EnrollmentRepositoryAdapter(JpaEnrollmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        EnrollmentEntity entity = toEntity(enrollment);
        EnrollmentEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Enrollment> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private EnrollmentEntity toEntity(Enrollment enrollment) {
        EnrollmentEntity entity = new EnrollmentEntity();
        entity.setId(enrollment.id());
        entity.setUserId(enrollment.userId());
        entity.setCourseId(enrollment.courseId());
        entity.setStatus(enrollment.status());
        entity.setCreatedAt(enrollment.createdAt());
        entity.setUpdatedAt(enrollment.updatedAt());
        return entity;
    }

    private Enrollment toDomain(EnrollmentEntity entity) {
        return Enrollment.fromPersistence(
                entity.getId(),
                entity.getUserId(),
                entity.getCourseId(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
