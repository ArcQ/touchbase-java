package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.event.CreatedChatEventData;
import com.kf.touchbase.models.domain.event.Event;
import com.kf.touchbase.models.domain.postgres.Chat;
import com.kf.touchbase.repository.BaseRepository;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@KafkaListener(groupId = "touchbaseCore", threads = 1)
public class EventsListenerImpl {

    private static final String CREATE_CHAT_COMPLETE = "CREATE_CHAT_COMPLETE";

    private final BaseRepository baseRepository;

    private Single<Event<CreatedChatEventData>> updateBaseWithId(
            Event<CreatedChatEventData> event) {
        CreatedChatEventData createdChatEventData = (CreatedChatEventData) event.getData();
        return baseRepository.findById(UUID.fromString(createdChatEventData.getReferenceId()))
                .flatMapSingle((base) -> {
                    if (base != null) {
                        base.getChats().add(new Chat(base, createdChatEventData.getChatId()));
                        return baseRepository.save(base).flatMap((savedBase) -> Single.just(event));
                    }
                    return Single.just(event);
                });
    }

    @Topic("chatpi-out")
    public Single<Event> receive(
            @KafkaKey String key,
            Single<Event> eventSingle) {
        return eventSingle.flatMap((event) -> {
            log.info(String.format(
                    "Got event from %s by %s at %s", event.getPublisher(), key,
                    event.getPublishedAt()));
            switch (key) {
                case CREATE_CHAT_COMPLETE:
                    @SuppressWarnings("unchecked")
                    Event<CreatedChatEventData> createdChatEvent =
                            (Event<CreatedChatEventData>) event;
                    return updateBaseWithId(createdChatEvent);
                default:
                    return Single.just(event);
            }
        });
    }
}
