package com.kf.touchbase.services.gamification;

public interface PointsService {
    SimplePointsSericeImpl.Reward calculateReward(RewardableAction rewardableAction);
}
