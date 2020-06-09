package com.kf.touchbase.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public abstract class TouchBaseEntity extends TouchBaseDomain{
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
}
