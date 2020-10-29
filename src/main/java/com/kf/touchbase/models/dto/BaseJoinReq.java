package com.kf.touchbase.models.dto;

import com.kf.touchbase.models.domain.postgres.BaseJoinAction;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.UUID;

@Value
@ToString
@EqualsAndHashCode
public class BaseJoinReq {
    UUID baseId;
    String userId;
    BaseJoinAction baseJoinAction;
}

