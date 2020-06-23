package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.TouchBaseDomainInterface;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class TouchBasePostgresDomain implements TouchBaseDomainInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @DateLong
    protected ZonedDateTime createdAt;

    @DateLong
    protected ZonedDateTime updatedAt;
}
