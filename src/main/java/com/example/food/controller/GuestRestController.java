package com.example.food.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.food.entity.Guest;
import com.example.food.repository.GuestRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestRestController {
	
	private final GuestRepository guestRepository;
    private final WebClient webClient;

    // 세션에서 Guest ID 반환
    @GetMapping("/id")
    public String getGuestId(HttpSession session) {
        if (session.getAttribute("guestId") == null) {
            String guestId = "guest-" + UUID.randomUUID().toString();
            session.setAttribute("guestId", guestId);
        }
        log.info("🔹 현재 Guest ID: {}", session.getAttribute("guestId"));
        return session.getAttribute("guestId").toString();
    }

    // 새로운 Guest 정보 저장 (DB에 저장)
    @PostMapping("/create")
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest, HttpSession session) {
        String guestId = (String) session.getAttribute("guestId"); // 세션에서 Guest ID 가져오기
        if (guestId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        guest.setId(guestId); // 세션 ID 설정
        guest.setCreatedAt(LocalDateTime.now()); // 생성 시간 추가

        Guest savedGuest = guestRepository.save(guest);
        log.info("✅ Guest 저장 완료: {}", savedGuest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGuest);
    }
    
    
    // ✅ 추천 API (Flask 호출) → JSON 반환하도록 수정
    @GetMapping("/recommend")
    public ResponseEntity<Map<String, Object>> getRecommendations(
            HttpSession session,
            @RequestParam("preferences") String preferences) {

        String guestId = (String) session.getAttribute("guestId");

        if (guestId == null) {
            log.warn("❌ Guest ID 없음! 먼저 사용자 정보를 저장해야 합니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "사용자 정보를 먼저 저장하세요!"));
        }

        // ✅ Flask API 호출
        Mono<Map> responseMono = webClient.post()
                .uri("/recommend")
                .bodyValue(Map.of("guestId", guestId, "preferences", preferences))
                .retrieve()
                .bodyToMono(Map.class);

        Map<String, Object> response = responseMono.block();
        log.info("✅ Flask 응답 데이터: {}", response);

        return ResponseEntity.ok(response); // ✅ JSON 응답 반환
    }
    
}
