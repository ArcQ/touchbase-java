package com.kf.touchbase.services.postgres;

import com.kf.touchbase.services.postgres.repository.UserRepository;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
public class MissonCommand {

    private static final Logger LOG = LoggerFactory.getLogger(MissonCommand.class);
    UserRepository userRepository;

    //twice a week
    @Scheduled(cron = "0 12 0 0 1,4")
    void executeTwiceAWeek() {
        LOG.info("Twice a week job: {}",
                new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
    }

    //weekly
    @Scheduled(cron = "0 12 0 0 2")
    void executeEveryFourtyFive() {
        LOG.info("Once a week job: {}",
                new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
    }

    void setMissions() {
//        Pageable pageRequest = Pageable.from(0, 200);
//        Page<User> onePage = userRepository.findAll(pageRequest);
//
//        while (!onePage.isEmpty()) {
//            pageRequest = pageRequest.next();
//            onePage.forEach(user ->);
//            onePage = userRepository.findAll(pageRequest);
//        }
    }
}
