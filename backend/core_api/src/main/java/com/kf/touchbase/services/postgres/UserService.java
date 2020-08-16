package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.services.postgres.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.persistence.EntityExistsException;

@RequiredArgsConstructor
@Singleton
public class UserService {

    private final UserRepository userRepository;

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public Iterable<User> searchByQuery(String query) {
        return userRepository.findByUsernameContainsOrFirstNameContainsOrLastNameContains(query, query, query);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByAuthId(String authId) {
        return userRepository.findByAuthId(authId);
    }

    public User create(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            throw new EntityExistsException("A user is already registered under this email.");
        }
        return userRepository.save(user);
    }
}
