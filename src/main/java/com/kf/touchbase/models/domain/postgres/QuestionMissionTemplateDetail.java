package com.kf.touchbase.models.domain.postgres;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class QuestionMissionTemplateDetail extends MissionTemplateDetail {

    public static class Question {
        UUID id;
        String displayText;
    }

    Integer difficulty;

    List<Question> questions;

    @Override
    int getComp() {
        return 100;
    }

    @Override
    int setProgress() {
        return 100;
    }
}
