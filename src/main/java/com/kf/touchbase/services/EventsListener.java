package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.event.Event;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@KafkaListener(groupId = "touchbaseCore", threads = 1)
public class EventsListener {

    private static final String CREATE_CHAT_COMPLETE = "CREATE_CHAT_COMPLETE";

    @Topic("chatpi-out")
    public Single<Event> receive(
            @KafkaKey String key,
            Single<Event> eventSingle) {
        return eventSingle.doOnSuccess((event) -> {
            log.info(String.format(
                    "Got event from %s by %s at %s", event.getPublisher(), key,
                    event.getPublishedAt()));
            switch (key) {
                case CREATE_CHAT_COMPLETE:
                    event.getData();
                default:
            }});
    }
}
