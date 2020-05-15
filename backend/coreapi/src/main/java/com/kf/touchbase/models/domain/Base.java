package com.kf.touchbase.models.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Base extends TouchBaseDomain {
    private String uuid;
    private String name;
    private double score;
    private String imageUrl;
    private Person hasMember;
    private Person createdBy;
    private Person ownedBy;
}
