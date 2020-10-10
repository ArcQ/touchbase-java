package com.kf.touchbase.models.domain.event;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class ChatEntityEvent {
    Object entity;
}
