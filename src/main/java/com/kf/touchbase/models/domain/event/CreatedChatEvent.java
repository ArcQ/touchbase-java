package com.kf.touchbase.models.domain.event;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class CreatedChatEvent {
    String chatId;
    String referenceId;
}
