package com.kf.touchbase.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PushTokenReq {
    @NotBlank
    @NotNull
    private String token;

    @NotBlank
    @NotNull
    private String deviceId;
}
