package com.kf.touchbase.models.dto;

import com.kf.touchbase.models.domain.postgres.MissonType;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MissionReq {
    private String name;
    private String description;
    private Double scoreReward;
    private MissonType missonType;
}
