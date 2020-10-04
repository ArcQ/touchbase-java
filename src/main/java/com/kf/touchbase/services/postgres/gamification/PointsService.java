package com.kf.touchbase.services.postgres.gamification;

public interface PointsService {
    SimplePointsSericeImpl.Reward calculateReward(RewardableAction rewardableAction);
}
