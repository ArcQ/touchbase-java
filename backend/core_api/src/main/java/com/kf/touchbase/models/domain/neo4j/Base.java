package com.kf.touchbase.models.domain.neo4j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
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
public class Base extends TouchBaseNeo4jEntity {

    private String name;
    private Double score;
    private String imageUrl;

    @Builder.Default()
    private boolean isActive = true;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties({"bases", "created", "owns"})
    @Relationship(type="HAS_MEMBER")
    private Set<Person> members;
}
