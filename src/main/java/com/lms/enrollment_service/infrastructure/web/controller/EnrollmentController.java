package com.lms.enrollment_service.infrastructure.web.controller;

import com.lms.enrollment_service.application.CreateEnrollmentUseCase;
import com.lms.enrollment_service.application.GetEnrollmentUseCase;
import com.lms.enrollment_service.application.ListEnrollmentsByUserUseCase;
import com.lms.enrollment_service.domain.model.Enrollment;
import com.lms.enrollment_service.infrastructure.web.dto.CreateEnrollmentRequest;
import com.lms.enrollment_service.infrastructure.web.dto.EnrollmentResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final CreateEnrollmentUseCase createEnrollmentUseCase;
    private final GetEnrollmentUseCase getEnrollmentUseCase;
    private final ListEnrollmentsByUserUseCase listEnrollmentsByUserUseCase;

    public EnrollmentController(
            CreateEnrollmentUseCase createEnrollmentUseCase,
            GetEnrollmentUseCase getEnrollmentUseCase,
            ListEnrollmentsByUserUseCase listEnrollmentsByUserUseCase) {
        this.createEnrollmentUseCase = createEnrollmentUseCase;
        this.getEnrollmentUseCase = getEnrollmentUseCase;
        this.listEnrollmentsByUserUseCase = listEnrollmentsByUserUseCase;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponse> createEnrollment(
            @Valid @RequestBody CreateEnrollmentRequest request) {
        Enrollment enrollment = createEnrollmentUseCase.execute(request.userId(), request.courseId());
        return ResponseEntity.ok(EnrollmentResponse.from(enrollment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> getEnrollment(@PathVariable Long id) {
        Enrollment enrollment = getEnrollmentUseCase.findById(id);
        return ResponseEntity.ok(EnrollmentResponse.from(enrollment));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> findByUser(@RequestParam Long userId) {
        List<EnrollmentResponse> enrollments = listEnrollmentsByUserUseCase.list(userId)
                .stream()
                .map(EnrollmentResponse::from)
                .toList();
        return ResponseEntity.ok(enrollments);
    }
}
