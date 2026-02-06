package com.lms.enrollment_service.infrastructure.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.enrollment_service.application.UpdateEnrollmentStatusUseCase;
import com.lms.enrollment_service.infrastructure.config.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventConsumer {

    private final ObjectMapper objectMapper;
    private final UpdateEnrollmentStatusUseCase updateUseCase;

    public PaymentEventConsumer(ObjectMapper objectMapper, UpdateEnrollmentStatusUseCase updateUseCase) {
        this.objectMapper = objectMapper;
        this.updateUseCase = updateUseCase;
    }

    @KafkaListener(topics = KafkaTopics.PAYMENT_EVENTS, groupId = "enrollment-service")
    public void handlePaymentEvent(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            Long enrollmentId = node.path("enrollmentId").asLong();
            String eventType = node.path("eventType").asText();
            updateUseCase.handlePaymentEvent(enrollmentId, eventType);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to process payment event", e);
        }
    }
}
