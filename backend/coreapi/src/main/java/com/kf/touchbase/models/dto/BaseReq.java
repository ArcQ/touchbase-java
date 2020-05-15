package com.kf.touchbase.models.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@EqualsAndHashCode
public class BaseReq {
    String uuid;
    String name;
    double score;
    String imageUrl;
}
