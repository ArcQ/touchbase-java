package com.kf.touchbase.models.dto;

import lombok.*;

import java.util.UUID;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MissionReq {
    UUID id;
    //todo requires validation here where isStarted must not be false and value must be positive
    Integer value;
}
