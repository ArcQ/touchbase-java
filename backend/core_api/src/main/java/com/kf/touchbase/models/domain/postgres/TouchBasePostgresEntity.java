package com.kf.touchbase.models.domain.postgres;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Set;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MappedSuperclass
public abstract class TouchBasePostgresEntity extends TouchBasePostgresDomain {
    @ManyToOne(fetch = FetchType.LAZY)
    private Person creator;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Person> owners;
}
