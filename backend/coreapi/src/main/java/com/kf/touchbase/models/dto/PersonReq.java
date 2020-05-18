package com.kf.touchbase.models.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@EqualsAndHashCode
public class PersonReq {
    String email;
    Double score;
    String firstName;
    String lastName;
    String username;
}
