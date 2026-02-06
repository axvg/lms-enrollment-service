package com.lms.enrollment_service.application;

import com.lms.enrollment_service.domain.event.EnrollmentCreatedEvent;
import com.lms.enrollment_service.domain.event.EventMetadata;
import com.lms.enrollment_service.domain.exception.EnrollmentValidationException;
import com.lms.enrollment_service.domain.model.Enrollment;
import com.lms.enrollment_service.domain.port.CourseServiceClient;
import com.lms.enrollment_service.domain.port.CourseServiceClient.CourseSummary;
import com.lms.enrollment_service.domain.port.EnrollmentEventPublisher;
import com.lms.enrollment_service.domain.port.EnrollmentRepository;
import com.lms.enrollment_service.domain.port.UserServiceClient;
import com.lms.enrollment_service.domain.port.UserServiceClient.UserSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateEnrollmentUseCase {

    private final EnrollmentRepository repository;
    private final CourseServiceClient courseServiceClient;
    private final UserServiceClient userServiceClient;
    private final EnrollmentEventPublisher eventPublisher;

    public CreateEnrollmentUseCase(
            EnrollmentRepository repository,
            CourseServiceClient courseServiceClient,
            UserServiceClient userServiceClient,
            EnrollmentEventPublisher eventPublisher) {
        this.repository = repository;
        this.courseServiceClient = courseServiceClient;
        this.userServiceClient = userServiceClient;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Enrollment execute(Long userId, Long courseId) {
        UserSummary user = userServiceClient.findUser(userId)
                .orElseThrow(() -> new EnrollmentValidationException("User not found: " + userId));

        if (!"ACTIVE".equalsIgnoreCase(user.status())) {
            throw new EnrollmentValidationException("User is not active: " + userId);
        }

        CourseSummary course = courseServiceClient.findCourse(courseId)
                .orElseThrow(() -> new EnrollmentValidationException("Course not found: " + courseId));

        if (!course.published()) {
            throw new EnrollmentValidationException("Course is not published: " + courseId);
        }

        Enrollment enrollment = Enrollment.create(userId, courseId);
        Enrollment saved = repository.save(enrollment);

        eventPublisher.publishCreated(
                new EnrollmentCreatedEvent(
                        saved.id(),
                        saved.userId(),
                        saved.courseId(),
                        saved.status().name()
                ),
                EventMetadata.of("EnrollmentCreatedEvent")
        );

        return saved;
    }
}
