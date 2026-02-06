package com.lms.enrollment_service.infrastructure.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.enrollment_service.domain.event.EventMetadata;
import com.lms.enrollment_service.domain.event.EnrollmentCreatedEvent;
import com.lms.enrollment_service.domain.event.EnrollmentUpdatedEvent;
import com.lms.enrollment_service.domain.port.EnrollmentEventPublisher;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.lms.enrollment_service.infrastructure.config.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEnrollmentEventPublisher implements EnrollmentEventPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEnrollmentEventPublisher(
            ObjectMapper objectMapper,
            KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishCreated(EnrollmentCreatedEvent event, EventMetadata metadata) {
        publish(event.enrollmentId().toString(), event, metadata);
    }

    @Override
    public void publishUpdated(EnrollmentUpdatedEvent event, EventMetadata metadata) {
        publish(event.enrollmentId().toString(), event, metadata);
    }

    private void publish(String key, Object event, EventMetadata metadata) {
        try {
            WrappedEvent payload = new WrappedEvent(event, metadata);
            String json = objectMapper.writeValueAsString(payload);
            kafkaTemplate.send(KafkaTopics.ENROLLMENT_EVENTS, key, json);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize event", e);
        }
    }

    private record WrappedEvent(Object data, EventMetadata metadata) {}
}
