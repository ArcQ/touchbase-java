package com.kf.touchbase.models.dto;

import com.kf.touchbase.models.domain.postgres.BaseJoinAction;
import lombok.*;

import java.util.UUID;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BaseJoinReq {
    UUID baseId;
    String userAuthKey;
    BaseJoinAction baseJoinAction;
}

