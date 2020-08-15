package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.services.OwnedRepositoryService;

import javax.inject.Singleton;

@Singleton
public interface BaseRepository extends OwnedRepositoryService<Base> {}
