package com.kf.touchbase.models.domain.neo4j;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.neo4j.driver.internal.shaded.reactor.util.annotation.Nullable;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NodeEntity
@NoArgsConstructor
public class Person extends TouchBaseNeo4jDomain {

    private String authId;
    private String username;
    private String email;
    private Double score;

    @Nullable
    private String firstName;
    @Nullable
    private String lastName;

    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="HAS_MEMBER", direction = Relationship.INCOMING)
    private Set<Base> bases;

    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="CREATED_BY", direction = Relationship.INCOMING)
    private Set<Base> created;

    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="OWNED_BY", direction = Relationship.INCOMING)
    private Set<Base> owns;
}
