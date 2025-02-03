package com.example.food.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class RecommendService {

	private final WebClient webClient;

	public RecommendService(WebClient.Builder webClientBuilder) {
		/*
		 * this.webClient = webClientBuilder.baseUrl("http://localhost:8000").build();
		 */
		this.webClient = webClientBuilder.baseUrl("https://python-q9k6.onrender.com").build();
	}

	public Mono<Map> getRecommendations(String guestId, String preferences) {
	      return webClient.post()
	                .uri("/recommend")
	                .bodyValue(Map.of("guestId", guestId, "preferences", preferences))
	                .retrieve()
	                .bodyToMono(Map.class); // JSON 응답을 Map으로 변환
	}
}
