package com.kf.touchbase.rest;

import com.kf.touchbase.mappers.UserMapper;
import com.kf.touchbase.models.domain.event.UserEvent;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.UserReq;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.services.TouchbaseEventPublisher;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller("/api/v1/user")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TouchbaseEventPublisher touchbaseEventPublisher;

    //    @Get("/{?query}")
    //    @Produces(MediaType.APPLICATION_JSON)
    //    @Operation(summary = "Search for users to add")
    //    @ExecuteOn(TaskExecutors.IO)
    //    public List<UserPublicRes> findAllPublic(Optional<String> query) {
    //        List<User> userList = query.isPresent()
    //                ? userRepository.findByUsernameContainsOrFirstNameContainsOrLastNameContains(
    //                        query.get(), query.get(), query.get())
    //                : userRepository.findAll();
    //
    //        return userMapper.userListToUserPublicResList(userList);
    //    }

    @Get("/me")
    @ExecuteOn(TaskExecutors.IO)
    public Single<User> getMe(Authentication authentication) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMapMaybe(userRepository::findByAuthKey)
                .switchIfEmpty(Single.error(AuthenticationException::new));
    }

    @Operation(summary = "Create user")
    @ExecuteOn(TaskExecutors.IO)
    @Post
    public Single<User> postUser(Authentication authentication, @Body UserReq userReq) {
        var newUser = userMapper.userReqToUser(userReq);
        //TODO, if update username or email, should update cognito as well
        return createOrUpdateUser(authentication, newUser);
    }

    @Operation(summary = "Update user")
    @ExecuteOn(TaskExecutors.IO)
    @Patch
    public Single<User> patchUser(Authentication authentication, @Body UserReq userReq) {
        var newUser = userMapper.userReqToUser(userReq);
        return createOrUpdateUser(authentication, newUser);
    }

    private Single<User> createOrUpdateUser(Authentication authentication, User newUser) {
        //TODO, if update username or email, should update cognito as well
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMapMaybe(userRepository::findByAuthKey)
                .switchIfEmpty(AuthUtils.buildNewUser(authentication))
                .flatMap(user -> {
                    touchbaseEventPublisher.publishEvent(new UserEvent(user));
                    if (user.getId() == null) {
                        return userRepository.save(user.merge(newUser));
                    }
                    return userRepository.update(user.merge(newUser));
                });
    }
}
