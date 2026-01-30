package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.example.demo.websocket.CustomWebSocketHandler;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer, WebMvcConfigurer {

    @Bean
    public CustomWebSocketHandler customWebSocketHandler() {
        return new CustomWebSocketHandler();
    }
    
    
    

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("WebSocket handler registered");
        registry.addHandler(customWebSocketHandler(), "/chat")
        		.addInterceptors(new JwtHandshakeInterceptor()) // JWT 인터셉터
        		.setAllowedOrigins("http://localhost:3000"); // React 앱 도메인;
                //.setAllowedOrigins("*"); // 개발용, 운영 시 특정 도메인으로 변경
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
                //.allowCredentials(true);  우분투 테스트
    }
}
