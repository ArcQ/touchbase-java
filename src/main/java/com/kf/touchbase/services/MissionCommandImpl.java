package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.MissionTemplate;
import com.kf.touchbase.models.domain.postgres.Mission;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.MissionRepository;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class MissionCommandImpl {

    BaseMemberRepository baseMemberRepository;
    MissionRepository missionRepository;

   private List<MissionTemplate> generateMissionTemplates(User user) {
       return Collections.singletonList(new MissionTemplate());
   }

    //twice a week
    @Scheduled(cron = "0 12 0 0 1,4")
    void setMissions() {
        log.info("Twice a week job: {}",
                new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        baseMemberRepository.findAll()
                .flatMapIterable((baseMember) -> generateMissionTemplates(baseMember.getUser())
                        .stream()
                        .map((missionTemplate) ->
                                Mission.builder()
                                        .assignedUser(baseMember.getUser())
                                        .assignedBase(baseMember.getBase())
                                        .storedMissionTemplate(missionTemplate)
                                        .build())
                        .collect(Collectors.toList()))
                .buffer(5)
                .flatMap(missionRepository::saveAll).subscribe();
    }
}
