package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Base;

public interface BaseService extends DataService<Base> {
    Iterable<Base> findAll();

    Class<Base> getEntityType();
}
