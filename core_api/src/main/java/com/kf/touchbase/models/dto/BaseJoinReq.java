package com.kf.touchbase.models.dto;

import com.kf.touchbase.models.domain.postgres.BaseJoinAction;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@EqualsAndHashCode
public class BaseJoinReq {
    String baseId;
    String userId;
    BaseJoinAction baseJoinAction;
}

