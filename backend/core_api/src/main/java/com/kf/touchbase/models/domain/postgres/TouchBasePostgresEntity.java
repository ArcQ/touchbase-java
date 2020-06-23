package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.TouchBaseEntityInterface;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class TouchBasePostgresEntity extends TouchBasePostgresDomain implements TouchBaseEntityInterface {
    private String creatorId;

    private String ownerId;

    @Override
    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public String getCreatorId() {
        return creatorId;
    }
}
