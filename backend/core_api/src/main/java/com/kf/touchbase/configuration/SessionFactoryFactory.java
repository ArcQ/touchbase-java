package com.kf.touchbase.configuration;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;


@Factory
@NoArgsConstructor
public class SessionFactoryFactory {

	@Value("${neo4j.uri}")
	String uri;

	@Value("${neo4j.username}")
	String username;

	@Value("${neo4j.password}")
	String password;

	@Value("${neo4j.encryption.level}")
	String encryptionLevel;

	@Bean(preDestroy = "close")
	public SessionFactory sessionFactory() {
		Configuration configuration = new Configuration.Builder()
				.uri(uri)
				.credentials(username, password)
				.encryptionLevel(encryptionLevel)
				.build();
		return new SessionFactory(configuration, "com.kf.touchbase.models.domain");
	}
}
