package com.kf.touchbase.models.domain.postgres;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_user")
public class User extends TouchBasePostgresDomain {

    private String authId;
    private String username;

    private String email;
    private Double score;

    private String firstName;
    private String lastName;

    private String imageUrl;

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