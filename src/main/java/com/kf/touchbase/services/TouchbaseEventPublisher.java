package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.event.ChatEntityEventData;
import com.kf.touchbase.models.domain.event.Event;
import com.kf.touchbase.models.domain.event.UserEventData;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

//@NotificationClient
@RequiredArgsConstructor
@Singleton
public class TouchbaseEventPublisher {
    private final EventPublisher eventPublisher;

    static String UPSERT_CHAT_ENTITY = "upsert-chat-entity";
    static String UPSERT_USER = "upsert-user";

    public void publishEvent(ChatEntityEventData eventData) {
        eventPublisher.publishMessage(UPSERT_CHAT_ENTITY, new Event(eventData));
    }

    public void publishEvent(UserEventData userEventData) {
        eventPublisher.publishMessage(UPSERT_USER, new Event(userEventData));
    }
}
