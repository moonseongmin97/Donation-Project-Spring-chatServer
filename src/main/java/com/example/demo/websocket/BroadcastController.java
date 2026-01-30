package com.example.demo.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class BroadcastController {

    private final CustomWebSocketHandler webSocketHandler;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 브로드캐스트 메시지 저장용 글로벌 키
    private static final String BROADCAST_KEY = "chat:broadcast";

    /**
     * 전체 사용자에게 브로드캐스트 (Consumer에서 호출)
     */
    @PostMapping("/broadcast")
    public ResponseEntity<String> broadcast(@RequestBody Map<String, Object> message) {
        try {
            log.info("브로드캐스트 요청 수신: {}", message);

            String jsonMessage = objectMapper.writeValueAsString(message);

            // 1. Redis에 저장 (나중에 접속하는 사용자도 볼 수 있게)
            redisTemplate.opsForList().rightPush(BROADCAST_KEY, jsonMessage);
            log.info("Redis 저장 완료 - key: {}", BROADCAST_KEY);

            // 2. 현재 접속 중인 사용자에게 실시간 전송
            webSocketHandler.broadcastToAll(jsonMessage);

            log.info("브로드캐스트 완료");
            return ResponseEntity.ok("Broadcast sent successfully");
        } catch (Exception e) {
            log.error("브로드캐스트 실패", e);
            return ResponseEntity.internalServerError().body("Broadcast failed: " + e.getMessage());
        }
    }
}
