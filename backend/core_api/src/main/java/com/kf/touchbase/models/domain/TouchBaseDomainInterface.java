package com.kf.touchbase.models.domain;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface TouchBaseDomainInterface {
    UUID getUuid();

    ZonedDateTime getCreatedAt();

    ZonedDateTime getUpdatedAt();
}
