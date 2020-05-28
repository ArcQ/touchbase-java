package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.Person;
import com.kf.touchbase.services.PersonService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller("/api/v1/person/")
@Secured(SecurityRule.IS_ANONYMOUS)
public class PersonController {

    private final PersonService personService;

    @Get("me")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getMe(Authentication authentication) {
        return personService.getByAuthId(authentication.getName());
    }

    @Get("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPerson(String username) {
        return personService.getByUsername(username);
    }
}
