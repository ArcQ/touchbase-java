package com.kf.touchbase.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import java.time.ZonedDateTime;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class TouchBaseDomain {
    @Id
    @GeneratedValue
    private String uuid;

    @DateLong
    protected ZonedDateTime createdAt;

    @DateLong
    protected ZonedDateTime updatedAt;
}
