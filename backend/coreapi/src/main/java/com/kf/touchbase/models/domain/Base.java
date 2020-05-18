package com.kf.touchbase.models.domain;

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
public class Base extends TouchBaseDomain {

    private String name;
    private Double score;
    private String imageUrl;

    @Relationship(type="HAS_MEMBER", direction = Relationship.INCOMING)
    private Set<Person> members;

    @Relationship(type="CREATED_BY")
    private Person creator;

    @Relationship(type="OWNED_BY")
    private Person owner;
}
