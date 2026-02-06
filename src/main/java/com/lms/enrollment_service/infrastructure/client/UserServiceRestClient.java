package com.lms.enrollment_service.infrastructure.client;

import com.lms.enrollment_service.domain.port.UserServiceClient;
import com.lms.enrollment_service.infrastructure.client.dto.UserResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceRestClient implements UserServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public UserServiceRestClient(
            RestTemplate restTemplate,
            @Value("${services.user.base-url:http://localhost:8082/api/users}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public Optional<UserSummary> findUser(Long userId) {
        String url = baseUrl + "/" + userId;
        UserResponse response = restTemplate.getForObject(url, UserResponse.class);
        return Optional.ofNullable(response)
                .map(user -> new UserSummary(user.id(), user.fullName(), user.email(), user.status()));
    }
}
