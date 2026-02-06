package com.lms.enrollment_service.infrastructure.client;

import com.lms.enrollment_service.domain.port.CourseServiceClient;
import com.lms.enrollment_service.infrastructure.client.dto.CourseResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CourseServiceRestClient implements CourseServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public CourseServiceRestClient(
            RestTemplate restTemplate,
            @Value("${services.course.base-url:http://localhost:8081/api/courses}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public Optional<CourseSummary> findCourse(Long courseId) {
        String url = baseUrl + "/" + courseId;
        CourseResponse response = restTemplate.getForObject(url, CourseResponse.class);
        return Optional.ofNullable(response)
                .map(course -> new CourseSummary(course.id(), course.title(), course.published()));
    }
}
