package com.kf.touchbase.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data()
public class SendPushReq {
    @NotNull
    private String userAuthKey;

    @Data()
    public static class PushBody {
        @NotBlank
        @JsonProperty("title")
        private String title;
        @NotBlank
        @JsonProperty("body")
        private String body;
    }

    @Valid
    @NotNull
    private PushBody body;
}
