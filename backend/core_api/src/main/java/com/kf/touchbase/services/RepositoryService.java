package com.kf.touchbase.services;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryService<T> {

    Iterable<T> findAll();

    Optional<T> findById(UUID id);

    void deleteById(UUID id);

    T save(T object);
}
