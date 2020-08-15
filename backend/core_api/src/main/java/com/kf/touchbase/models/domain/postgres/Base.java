package com.kf.touchbase.models.domain.postgres;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Base extends TouchBasePostgresEntity {

    private String name;

    @Builder.Default()
    private Double score = 0.0;
    private String imageUrl;

    @Builder.Default()
    private boolean isActive = true;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties({"bases", "created", "owns"})
    @ManyToMany
    private Set<Person> members;

    public void mergeInNotNull(Base base) {
        name = (base.name == null) ? name : base.name;
        imageUrl = (base.imageUrl == null) ? imageUrl : base.imageUrl;
    }
}
