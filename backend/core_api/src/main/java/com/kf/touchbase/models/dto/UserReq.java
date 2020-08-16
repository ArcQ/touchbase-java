package com.kf.touchbase.models.dto;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {
    String authId;
    String email;
    Double score;
    String firstName;
    String lastName;
    String username;
}

