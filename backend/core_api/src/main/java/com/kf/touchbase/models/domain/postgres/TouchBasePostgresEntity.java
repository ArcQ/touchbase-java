package com.kf.touchbase.models.domain.postgres;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MappedSuperclass
public abstract class TouchBasePostgresEntity extends TouchBasePostgresDomain {
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="entity_admin")
    private Set<User> admins;
}
