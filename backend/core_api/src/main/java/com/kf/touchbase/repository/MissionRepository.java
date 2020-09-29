package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.Mission;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;

import java.util.UUID;

public interface MissionRepository extends ReactiveStreamsCrudRepository<Mission, UUID> {

}
