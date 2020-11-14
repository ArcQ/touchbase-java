package com.kf.touchbase.models.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.UUID;

@Value
@ToString
@EqualsAndHashCode
public class ProgressReq {
    UUID baseId;
    Integer value;
}
