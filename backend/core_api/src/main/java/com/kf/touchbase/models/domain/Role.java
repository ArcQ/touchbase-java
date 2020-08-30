package com.kf.touchbase.models.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    MEMBER, ADMIN;

    @JsonValue
    public String getName() {
        return this.name();
    }
}
