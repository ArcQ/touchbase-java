package com.kf.touchbase.workers;

import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.domain.postgres.Mission;
import com.kf.touchbase.models.domain.postgres.MissionTemplate;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.MissionRepository;
import com.kf.touchbase.repository.MissionTemplateRepository;
import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.Maybe;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@Singleton
@Slf4j
public class MissionJobsImpl {

    BaseMemberRepository baseMemberRepository;
    MissionRepository missionRepository;
    MissionTemplateRepository missionTemplateRepository;

    private Supplier<Integer> getRandomIndexLambda(List<MissionTemplate> list) {
        return () -> ThreadLocalRandom.current()
                .nextInt(0, list.size() - 1);
    }

    private Maybe<BaseMember> filterMembersWhoNeedNewMissions(BaseMember baseMember) {
        return missionRepository.findAllByUserAuthKeyByBaseId(
                baseMember.getUser()
                        .getAuthKey(), baseMember.getBase()
                        .getId())
                .toList()
                .flatMapMaybe((missions) -> missions.size() > 0 ? Maybe.empty() :
                        Maybe.just(baseMember));
    }

    //twice a week
    @Scheduled(cron = "0 30 4 1/1 * ?")
    void setMissions() {
        log.info("Twice a week job: {}",
                new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        missionRepository.findAll()
                .filter((mission) -> mission.getExpiresAt()
                        .isAfter(LocalDateTime.now()))
                .forEach((mission) -> missionRepository.delete(mission));

        List<MissionTemplate> missionTemplates = missionTemplateRepository.findAll()
                .toList()
                .blockingGet();

        var getRandomIndex = getRandomIndexLambda(missionTemplates);

        baseMemberRepository.findAll()
                .flatMapMaybe(this::filterMembersWhoNeedNewMissions)
                .map((baseMember) -> Mission.builder()
                        .assignedUser(baseMember.getUser())
                        .assignedBase(baseMember.getBase())
                        .missionTemplate(missionTemplates.get(getRandomIndex.get()))
                        .expiresAt(LocalDateTime.now()
                                .plusDays(3))
                        .build())
                .buffer(5)
                .flatMap(missionRepository::saveAll)
                .subscribe();
    }
}
