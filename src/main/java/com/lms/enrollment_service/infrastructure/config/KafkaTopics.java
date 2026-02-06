package com.lms.enrollment_service.infrastructure.config;

public final class KafkaTopics {
    private KafkaTopics() {}

    public static final String ENROLLMENT_EVENTS = "lms.enrollment.events";
    public static final String PAYMENT_EVENTS = "lms.payment.events";
}
