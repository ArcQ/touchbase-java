package com.kf.touchbase.models.domain.postgres;

import com.kinoroy.expo.push.Priority;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

@Data
@SuperBuilder
@NoArgsConstructor
public class FinevoPushMessage {
    private HashMap<String, Object> data;
    private String title;
    private String body;
    private String sound;
    private Duration ttl;
    private Instant expiration;
    private Priority priority;
    private Integer badge;
    private String channelId;
}
