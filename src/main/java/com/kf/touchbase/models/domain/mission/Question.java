package com.kf.touchbase.models.domain.mission;

import com.kf.touchbase.models.domain.postgres.TouchBasePostgresDomain;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@Entity
public class Question extends TouchBasePostgresDomain {

    private UUID id;

    private Integer difficulty;

    private String displayText;

    private List<String> choices;

    private QuestionType questionType;

    public enum QuestionType {
        OPEN_ENDED, MULTIPLE_CHOICE
    }

}
