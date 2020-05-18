package com.kf.touchbase.configuration;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.neo4j.driver.Driver;
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver;
import org.neo4j.ogm.session.SessionFactory;


@Factory
public class SessionFactoryFactory {

	private final Driver driver;

	public SessionFactoryFactory(Driver driver) {
		this.driver = driver;
	}

	@Bean(preDestroy = "close")
	public SessionFactory sessionFactory() {
		return new SessionFactory(new BoltDriver(driver), "com.kf.touchbase.models.domain");
	}
}
