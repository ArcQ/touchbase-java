package com.kf.touchbase.workers;

import com.kf.touchbase.models.domain.postgres.Mission;
import com.kf.touchbase.models.domain.postgres.MissionTemplate;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.MissionRepository;
import com.kf.touchbase.repository.MissionTemplateRepository;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class MissionJobsImpl {

    BaseMemberRepository baseMemberRepository;
    MissionRepository missionRepository;
    MissionTemplateRepository missionTemplateRepository;

    //twice a week
    @Scheduled(cron = "0 30 4 1/1 * ?")
    void setMissions() {
        log.info("Twice a week job: {}",
                new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        List<MissionTemplate> missionTemplates = missionTemplateRepository.findAll()
                .toList()
                .blockingGet();
        baseMemberRepository.findAll()
                .flatMapIterable((baseMember) -> missionTemplates
                        .stream()
                        .map((missionTemplate) ->
                                Mission.builder()
                                        .assignedUser(baseMember.getUser())
                                        .assignedBase(baseMember.getBase())
                                        .build())
                        .collect(Collectors.toList()))
                .buffer(5)
                .flatMap(missionRepository::saveAll)
                .subscribe();
    }
}
