package com.lms.enrollment_service.domain.port;

import java.util.Optional;

public interface UserServiceClient {
    Optional<UserSummary> findUser(Long userId);

    record UserSummary(Long id, String fullName, String email, String status) {}
}
