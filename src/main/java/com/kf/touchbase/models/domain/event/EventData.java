package com.kf.touchbase.models.domain.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EventData {
    private String apiKey;
    private String apiSecret;
}
