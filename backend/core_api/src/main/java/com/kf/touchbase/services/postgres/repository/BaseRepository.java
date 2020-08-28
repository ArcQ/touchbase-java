package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.services.OwnedRepositoryService;
import io.micronaut.data.annotation.Repository;

import javax.inject.Singleton;

@Repository
public interface BaseRepository extends OwnedRepositoryService<Base> {}
