package com.kf.touchbase.workers;

import com.kf.touchbase.models.domain.postgres.MissionTemplate;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Singleton
@Slf4j
public class NewsJobsImpl {

    NewsRepository newsRepository;

    private List<MissionTemplate> generateMissionTemplates(User user) {
        return Collections.singletonList(new MissionTemplate());
    }

    // every few hours
    void updateNewsInterDay() {}

    void updateNewsDaily() {}
}