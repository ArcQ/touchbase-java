package com.kf.touchbase.models.domain.neo4j;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kf.touchbase.models.domain.TouchBaseEntityInterface;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.Relationship;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class TouchBaseNeo4jEntity extends TouchBaseNeo4jDomain implements TouchBaseEntityInterface {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="CREATED_BY")
    @JsonIgnoreProperties({"bases", "created", "owns"})
    private Person creator;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="OWNED_BY")
    @JsonIgnoreProperties({"bases", "created", "owns"})
    private Person owner;

    @Override
    @JsonIgnore
    public String getOwnerId() {
        return getOwner().getAuthId();
    }

    @Override
    @JsonIgnore
    public String getCreatorId() {
        return getCreator().getAuthId();
    }
}
