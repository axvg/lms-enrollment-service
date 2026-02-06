package com.lms.enrollment_service.domain.port;

import java.util.Optional;

public interface CourseServiceClient {
    Optional<CourseSummary> findCourse(Long courseId);

    record CourseSummary(Long id, String title, boolean published) {}
}
