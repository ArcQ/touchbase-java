package com.kf.touchbase.rest;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/person")
public class PersonController {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public String getPerson() {
        return "Hello World";
    }
}
