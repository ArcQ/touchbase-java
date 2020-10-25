package com.kf.touchbase.models.domain.event;

import com.kf.touchbase.models.domain.postgres.User;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserEventData extends EventData {
    User user;
}
