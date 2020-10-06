package com.kf.touchbase.models.domain.postgres;

import lombok.ToString;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@ToString
public class Event {

    String publisher = "touchbase";

    LocalDateTime publishedAt;

    Object data;

    public Event(Object data) {
        this.publishedAt = LocalDateTime.now();
        this.data = data;
    }
}
