package com.kf.touchbase.models.dto;

import com.kf.touchbase.models.domain.Role;
import lombok.*;

import java.util.UUID;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MemberReq {
    private UUID userId;
    private Role role;
}
