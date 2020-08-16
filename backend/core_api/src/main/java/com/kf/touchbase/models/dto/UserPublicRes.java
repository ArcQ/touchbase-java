package com.kf.touchbase.models.dto;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserPublicRes {
    Double score;
    String firstName;
    String lastName;
    String username;
}

