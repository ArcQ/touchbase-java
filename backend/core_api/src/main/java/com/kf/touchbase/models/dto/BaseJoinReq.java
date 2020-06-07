package com.kf.touchbase.models.dto;

import com.kf.touchbase.models.domain.BaseJoinAction;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@EqualsAndHashCode
public class BaseJoinReq {
    //auth provider id (like cognito)
    String baseId;
    String userId;
    BaseJoinAction baseJoinAction;
}

