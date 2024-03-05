/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livk.graphql.r2dbc.controller;

import com.livk.testcontainers.PostgresqlContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * <p>
 * GreetingControllerTest
 * </p>
 *
 * @author livk
 */
@SpringBootTest
@AutoConfigureWebTestClient(timeout = "15000")
@Testcontainers(disabledWithoutDocker = true)
class GreetingControllerTest {

	@Container
	@ServiceConnection
	static PostgresqlContainer postgresql = new PostgresqlContainer().withEnv("POSTGRES_PASSWORD", "123456")
		.withDatabaseName("graphql");

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.username", postgresql::getUsername);
		registry.add("spring.r2dbc.password", postgresql::getPassword);
		registry.add("spring.r2dbc.url", () -> "r2dbc:postgres://" + postgresql.getHost() + ":"
				+ postgresql.getMappedPort(5432) + "/" + postgresql.getDatabaseName());
	}

	@Autowired
	WebTestClient webTestClient;

	@Value("${spring.graphql.path:/graphql}")
	String graphqlPath;

	WebGraphQlTester tester;

	@BeforeEach
	public void init() {
		WebTestClient.Builder builder = webTestClient.mutate().baseUrl(graphqlPath);
		tester = HttpGraphQlTester.builder(builder).build();
	}

	@Test
	@SuppressWarnings("rawtypes")
	void greetings() {
		// language=GraphQL
		String document = """
				subscription {
				  greetings
				}""";
		Map result = tester.document(document).execute().path("upstreamPublisher").entity(Map.class).get();
		assertNotNull(result);
	}

}
