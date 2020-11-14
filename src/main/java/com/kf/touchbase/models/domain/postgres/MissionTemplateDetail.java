package com.kf.touchbase.models.domain.postgres;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public abstract class MissionTemplateDetail {

    abstract int getComp();

    abstract int setProgress();
}
