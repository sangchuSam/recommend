package com.example.food.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Controller
@Log4j2
public class MainController {
	


	@GetMapping("/eatery")
	public String showMain() {
		return "eatery";
	}
	

	
	
}
