package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.Event;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@KafkaListener(groupId = "touchbaseCore", threads = 1)
public class EventsListener {

    @Topic("chatpi")
    public Single<Event> receive(
            @KafkaKey String key,
            Single<Event> eventSingle) {
        return eventSingle.doOnSuccess((event) ->
                log.info(String.format(
                        "Got event from %s by %s at %s", event.getPublisher(), key,
                        event.getPublishedAt()))
        );
    }
}
