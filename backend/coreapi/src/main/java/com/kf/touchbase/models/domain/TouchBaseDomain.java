package com.kf.touchbase.models.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class TouchBaseDomain {
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
}
