package com.kf.touchbase.models.domain.neo4j;

import com.kf.touchbase.models.domain.TouchBaseDomainInterface;
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
@NoArgsConstructor
public abstract class TouchBaseNeo4jDomain implements TouchBaseDomainInterface {
    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @Convert(org.neo4j.ogm.typeconversion.UuidStringConverter.class)
    private UUID uuid;

    @DateLong
    protected ZonedDateTime createdAt;

    @DateLong
    protected ZonedDateTime updatedAt;
}
