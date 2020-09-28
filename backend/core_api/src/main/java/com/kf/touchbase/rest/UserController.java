package com.kf.touchbase.rest;

import com.kf.touchbase.mappers.UserMapper;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller("/api/v1/user")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Single<User> getMe(Authentication authentication) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMapMaybe(userRepository::findByAuthKey)
                .switchIfEmpty(Single.error(AuthenticationException::new));
    }

//    @Post
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @ExecuteOn(TaskExecutors.MESSAGE_CONSUMER)
//    public User postUser(Authentication authentication, @Body UserReq userReq) {
//        var user = userMapper.userReqToUser(userReq);
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            throw new EntityExistsException("A user is already registered under this email.");
//        }
//        return userRepository.save(user);
//    }
}
