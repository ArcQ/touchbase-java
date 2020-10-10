package com.kf.touchbase.models.domain.event;

import com.kf.touchbase.models.domain.postgres.User;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class UserEvent {
    User user;
}
