package com.kf.touchbase.models.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseJoin extends TouchBaseEntity{
    String baseId;
    String userId;
    BaseJoinAction baseJoinAction;
}
