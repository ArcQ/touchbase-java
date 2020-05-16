package com.kf.touchbase.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("neo4j")
public class Neo4jConfiguration {
    private String uri;
    private String username;
    private String password;
}
