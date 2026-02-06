package com.lms.enrollment_service.infrastructure.config;

import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(KafkaTopicInitializer.class);

    private final String bootstrapServers;

    public KafkaTopicInitializer(
            @Value("${spring.kafka.bootstrap-servers:localhost:9092}") String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    @Override
    public void run(ApplicationArguments args) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(props)) {
            List<NewTopic> topics = List.of(
                    new NewTopic(KafkaTopics.ENROLLMENT_EVENTS, 3, (short) 1),
                    new NewTopic(KafkaTopics.PAYMENT_EVENTS, 3, (short) 1)
            );
            adminClient.createTopics(topics).all().get();
            log.info("Kafka topics ensured: {}", topics.stream().map(NewTopic::name).toList());
        } catch (Exception e) {
            log.warn("Kafka topic initialization skipped/failed: {}", e.getMessage());
        }
    }
}
