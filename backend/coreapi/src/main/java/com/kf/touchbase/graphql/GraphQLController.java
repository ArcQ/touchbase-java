package com.kf.touchbase.graphql;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/graphql")
public class GraphQLController {
    @Get
    @Produces(MediaType.APPLICATION_GRAPHQL)
    public String query() {
        return "Hello World";
    }
}
