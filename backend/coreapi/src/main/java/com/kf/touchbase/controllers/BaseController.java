package com.kf.touchbase.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/base")
public class BaseController {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public String getBases() {
        return "Hello World";
    }
}
