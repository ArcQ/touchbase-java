package com.kf.touchbase.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@NodeEntity
public class Person extends TouchBaseDomain{
    @Id
    private String email;
    private double score;
    private String firstName;
    private String lastName;
    private String username;

    @Relationship(type="IN_BASE")
    private Set<Base> base;

    @Relationship(type="HAS_MEMBER", direction = Relationship.INCOMING)
    private Set<Base> created;

    @Relationship(type="OWNED_BY", direction = Relationship.INCOMING)
    private Set<Base> owns;
}
