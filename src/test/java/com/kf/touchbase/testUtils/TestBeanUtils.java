package com.kf.touchbase.testUtils;

import com.kf.touchbase.models.domain.event.Event;
import com.kf.touchbase.services.EventPublisher;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class TestBeanUtils {

    public static void createTopics(String [] topics, String bootstrapServers) {
        var newTopics =
                Stream.of(topics).map((str) -> new NewTopic(str, 1, (short) 1)).collect(
                        Collectors.toList());
        var admin = AdminClient.create(Collections.singletonMap("bootstrap.servers", bootstrapServers));
        admin.createTopics(newTopics);
    }

    @Value
    @AllArgsConstructor
    public static class EventWithKey {
        String key;
        Event event;
    }

    @AllArgsConstructor
    public static class StubTouchbaseEventPublisher implements EventPublisher {

        @Getter
        private BlockingQueue<EventWithKey> messages;

        @Override
        public void publishMessage(@KafkaKey String key, Event event) {
            messages.add(new EventWithKey(key, event));
        };

        public void reset() {
            messages.clear();
        };
    }
}
