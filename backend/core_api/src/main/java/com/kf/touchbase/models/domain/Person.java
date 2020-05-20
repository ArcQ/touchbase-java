package com.kf.touchbase.models.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@NoArgsConstructor
@NodeEntity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
public class Person extends TouchBaseDomain{
    private String email;
    private Double score;
    private String username;

    @Nullable
    private String firstName;
    @Nullable
    private String lastName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="HAS_MEMBER", direction = Relationship.INCOMING)
    private Set<Base> bases;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="CREATED_BY", direction = Relationship.INCOMING)
    private Set<Base> created;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type="OWNED_BY", direction = Relationship.INCOMING)
    private Set<Base> owns;
}
