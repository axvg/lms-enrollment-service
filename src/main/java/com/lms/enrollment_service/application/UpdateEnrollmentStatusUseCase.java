package com.lms.enrollment_service.application;

import com.lms.enrollment_service.domain.event.EnrollmentUpdatedEvent;
import com.lms.enrollment_service.domain.event.EventMetadata;
import com.lms.enrollment_service.domain.exception.EnrollmentNotFoundException;
import com.lms.enrollment_service.domain.model.Enrollment;
import com.lms.enrollment_service.domain.port.EnrollmentEventPublisher;
import com.lms.enrollment_service.domain.port.EnrollmentRepository;
import com.lms.enrollment_service.domain.service.EnrollmentDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateEnrollmentStatusUseCase {

    private final EnrollmentRepository repository;
    private final EnrollmentEventPublisher eventPublisher;

    public UpdateEnrollmentStatusUseCase(
            EnrollmentRepository repository,
            EnrollmentEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Enrollment handlePaymentEvent(Long enrollmentId, String eventType) {
        Enrollment enrollment = repository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentId));

        Enrollment updated = EnrollmentDomainService.handlePayment(enrollment, eventType);
        Enrollment saved = repository.save(updated);

        eventPublisher.publishUpdated(
                new EnrollmentUpdatedEvent(saved.id(), saved.status().name()),
                EventMetadata.of(eventType)
        );
        return saved;
    }
}
