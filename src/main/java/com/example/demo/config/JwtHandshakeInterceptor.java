package com.example.demo.config;

import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;

import java.util.List;
import java.util.Map;


public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
    	//ip 넣는거 추가하자 나중에 
        List<String> cookies = request.getHeaders().get("Cookie");
        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.contains("jwtToken=")) {
                    // JWT 토큰 추출
                    String jwtToken = cookie.split("jwtToken=")[1].split(";")[0];
                    System.out.println("JWT 토큰: " + jwtToken);

                    // JWT 검증 로직 추가
                    if (validateJwtToken(jwtToken)) {
                        attributes.put("user",jwtToken ); // 사용자 정보 저장
                        return true; // 인증 성공
                    }
                }
            }
        }
        System.out.println("JWT 토큰 없음 또는 검증 실패");
        return true; // 인증 실패 시 
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        // 핸드셰이크 완료 후 작업
    }

    private boolean validateJwtToken(String token) {
        // JWT 검증 로직
        return true;
    }

    private String extractUserIdFromToken(String token) {
        // JWT에서 사용자 ID 추출
        return "user123";
    }
}
