package com.kf.touchbase.models.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.neo4j.ogm.id.UuidStrategy;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
@NoArgsConstructor
public abstract class TouchBaseDomain {
    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @Convert(org.neo4j.ogm.typeconversion.UuidStringConverter.class)
    private UUID uuid;

    @DateLong
    protected ZonedDateTime createdAt;

    @DateLong
    protected ZonedDateTime updatedAt;
}
