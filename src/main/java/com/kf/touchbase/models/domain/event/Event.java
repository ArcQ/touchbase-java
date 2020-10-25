package com.kf.touchbase.models.domain.event;

import lombok.ToString;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@ToString
public class Event<T extends EventData> {

    String publisher = "touchbase";

    LocalDateTime publishedAt;

    T data;

    public Event(T data) {
        this.publishedAt = LocalDateTime.now();
        this.data = data;
    }
}
