package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Base;

public interface BaseService {
    Iterable<Base> findAll();

    Class<Base> getEntityType();
}
