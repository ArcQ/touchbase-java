package com.kf.touchbase.models.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Person extends TouchBaseDomain{
    private String email;
    private double score;
    private String firstName;
    private String lastName;
    private String username;
    private Base inBase;
    private Base created;

    @Builder.Default
    private List<Base> owns = Collections.emptyList();
}
