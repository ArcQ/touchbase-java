package com.kf.touchbase.models.domain.postgres;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@MappedSuperclass
public abstract class TouchBasePostgresDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    protected ZonedDateTime createdAt;

    protected ZonedDateTime updatedAt;
}
