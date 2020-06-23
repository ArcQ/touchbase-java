package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.neo4j.Person;
import com.kf.touchbase.services.neo4j.PersonService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller("/api/v1/person/")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class PersonController {

    private final PersonService personService;

    @Get("me")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getMe(Authentication authentication) {
        return personService.findByUsername((String) authentication.getAttributes().get("username"));
    }

    @Get("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPerson(String username) {
        return personService.findByUsername(username);
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public Person postPerson(@Body Person person) {
        return personService.create(person);
    }
}
