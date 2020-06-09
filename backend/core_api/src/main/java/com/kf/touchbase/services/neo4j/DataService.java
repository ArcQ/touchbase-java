package com.kf.touchbase.services.neo4j;

import java.util.UUID;

public interface DataService<T> {

    Iterable<T> findAll();

    T find(UUID id);

    void delete(UUID id);

    T createOrUpdate(T object);
}
