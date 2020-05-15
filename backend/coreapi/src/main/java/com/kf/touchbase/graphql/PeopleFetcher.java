package com.kf.touchbase.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class PeopleFetcher implements DataFetcher {

    @Override
    public String get(DataFetchingEnvironment env) {
        String name = env.getArgument("name");
        if (name == null || name.trim().length() == 0) {
            name = "World";
        }
        return String.format("Hello %s!", name);
    }
}
