package com.kf.touchbase.models.dto;

import lombok.*;

import java.util.UUID;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MemberReq {
    private UUID baseId;
    private UUID userId;
}
