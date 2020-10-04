package com.kf.touchbase.services.postgres.gamification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Singleton
@Slf4j
public class SimplePointsSericeImpl implements PointsService {

    double LUCKY_MULTIPLER = 1.5;
    double LUCKY_CHANCE = 0.05;

    @Override
    public Reward calculateReward(RewardableAction rewardableAction) {
        var rewardMultipler = RewardMultiplier.getOne();
        var reward = rewardableAction.getScoreReward() * RewardMultiplier.getOne().getVal();
        return new Reward(reward, rewardMultipler);
    }

    @Getter
    @AllArgsConstructor
    public enum RewardMultiplier {
        NONE(1), THIRTY_PERCENT(1.3), FIFTY_PERCENT(1.5), ONE_HUNDRED_PERCENT(2);

        double val;

        static public RewardMultiplier getOne() {
            double rand = Math.random() * 100;
            if (rand < 50) {
                return NONE;
            } else if (rand < 80) {
                return THIRTY_PERCENT;
            } else if (rand < 90) {
                return FIFTY_PERCENT;
            }
            return ONE_HUNDRED_PERCENT;
        }

    }

    @Value
    @RequiredArgsConstructor
    public static class Reward {
        final double scoreReward;
        final RewardMultiplier rewardMultiplier;
    }
}
