package com.kf.touchbase.services;

import java.util.Collections;

public class BaseService extends GenericService<StudyBuddy> implements StudyBuddyService {
    @Override
    Iterable<StudyBuddy> findAll() {
        return session.loadAll(StudyBuddy, 1);
    }

    @Override
    Iterable<Map<String, Object>> getStudyBuddiesByPopularity() {
        String query = "MATCH (s:StudyBuddy)<-[:BUDDY]-(p:Student) return p, count(s) as buddies ORDER BY buddies DESC"
        return Neo4jSessionFactory.getInstance().getNeo4jSession().query(query, Collections.EMPTY_MAP) ;
    }

    @Override
    Class<StudyBuddy> getEntityType() {
        return StudyBuddy.class;
    }
}
