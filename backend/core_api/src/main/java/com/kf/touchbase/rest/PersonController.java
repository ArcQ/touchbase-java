package com.kf.touchbase.rest;

import com.kf.touchbase.mappers.PersonMapper;
import com.kf.touchbase.models.domain.postgres.Person;
import com.kf.touchbase.models.dto.PersonPublicRes;
import com.kf.touchbase.models.dto.PersonReq;
import com.kf.touchbase.services.postgres.PersonService;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Controller("/api/v1/person")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @Get("/{?query}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Search for users to add")
    public Iterable<PersonPublicRes> findAllPublic(Authentication authentication, Optional<String> query) {
        Iterable<Person> personIterable = query.isPresent()
                ? personService.searchByQuery(query.get())
                : personService.findAll();

        return personMapper.personIterableToPersonPublicResIterable(personIterable);
    }

    @Get("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getMe(Authentication authentication) {
        return personService.findByAuthId(AuthUtils.getAuthIdFromAuth(authentication));
    }

    @Get("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPerson(String username) {
        return personService.findByUsername(username);
    }

    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person postPerson(Authentication authentication, @Body PersonReq personReq) {
        var person = personMapper.personReqToPerson(personReq);
        return personService.create(person);
    }
}
