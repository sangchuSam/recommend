package com.example.food.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				.baseUrl("https://python-q9k6.onrender.com")
				/* .baseUrl("http://localhost:8000") */
				.build();
	}
}
