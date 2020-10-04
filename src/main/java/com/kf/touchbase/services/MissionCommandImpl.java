package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.Mission;
import com.kf.touchbase.repository.MissionRepository;
import com.kf.touchbase.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
@Slf4j
public class MissionCommandImpl {

    UserRepository userRepository;
    MissionRepository missionRepository;

    //twice a week
    //    @Scheduled(cron = "0 12 0 0 1,4")
    void executeTwiceAWeek() {
        log.info("Twice a week job: {}",
                new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
    }

    //weekly
    //    @Scheduled(cron = "0 12 0 0 2")
    //    void setMissions() {
    //        LOG.info("Starting to set missions | once a week job: {}",
    //                new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
    //        Pageable pageRequest = Pageable.from(0, 200);
    //        Page<User> onePage = userRepository.findAll(pageRequest);
    //
    //        while (!onePage.isEmpty()) {
    //            pageRequest = pageRequest.next();
    //            onePage.forEach(user ->);
    //            onePage = userRepository.findAll(pageRequest);
    //        }
    //    }

    Mission addProgress() {
        return null;
    }
}
