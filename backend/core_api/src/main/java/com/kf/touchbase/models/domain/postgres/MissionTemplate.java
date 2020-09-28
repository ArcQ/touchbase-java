package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.MissionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
//@Entity
public class MissionTemplate extends TouchBasePostgresDomain {
    private String name;
    private String description;
    private Double scoreReward;
    private MissionType missonType;
}