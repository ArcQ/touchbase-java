package com.kf.touchbase.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@NodeEntity
public class Base extends TouchBaseEntity {

    private String name;
    private Double score;
    private String imageUrl;
    private boolean isActive = true;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="HAS_MEMBER", direction = Relationship.INCOMING)
    @JsonIgnoreProperties({"bases", "created", "owns"})
    private Set<Person> members;
}
