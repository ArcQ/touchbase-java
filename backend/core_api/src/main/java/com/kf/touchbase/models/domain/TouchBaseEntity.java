package com.kf.touchbase.models.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@NodeEntity
public abstract class TouchBaseEntity extends TouchBaseDomain{
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="CREATED_BY")
    private Person creator;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="OWNED_BY")
    private Person owner;
}
