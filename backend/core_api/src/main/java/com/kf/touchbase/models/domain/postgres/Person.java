package com.kf.touchbase.models.domain.postgres;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person extends TouchBasePostgresDomain {

    private String authId;
    private String username;

    private String email;
    private Double score;

    private String firstName;
    private String lastName;

//    @ManyToMany
//    private Set<Base> bases;
//
//    @OneToMany
//    private Set<Base> created;
//
////    @JsonManagedReference
////    @ToString.Exclude
////    @EqualsAndHashCode.Exclude
//    @ManyToMany
//    private Set<Base> owns;
}
