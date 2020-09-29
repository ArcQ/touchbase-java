package com.kf.touchbase.models.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class MissionTemplate {

    private String name;

    private String description;

    private Double scoreReward;

    @Enumerated(EnumType.STRING)
    private MissionType missionType;
}
