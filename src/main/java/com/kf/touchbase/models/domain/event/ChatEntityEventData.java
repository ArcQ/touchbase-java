package com.kf.touchbase.models.domain.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@EqualsAndHashCode(callSuper = false)
public class ChatEntityEventData extends EventData {
    Object entity;
}
