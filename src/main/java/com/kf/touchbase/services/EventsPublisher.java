package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.Event;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import org.apache.kafka.clients.producer.ProducerConfig;

//@NotificationClient
@KafkaClient(
        id="touchbase-client",
        acks = KafkaClient.Acknowledge.ALL,
        properties = @Property(name = ProducerConfig.RETRIES_CONFIG, value = "5")
)
public interface EventsPublisher {

    @Topic("touchbase-app")
    void publishMessage(@KafkaKey String key, Event user);
}
