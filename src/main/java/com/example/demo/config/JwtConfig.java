package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



@Configuration
public class JwtConfig {
	
    //private static final String SECRET_KEY = "yourSecretKey";  // 실제 환경에서는 안전하게 관리 필요
    //private static final long EXPIRATION_TIME = 1000 * 60 * 60;  // 1시간

	
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime; // 예: 1시간 (3600000ms)

    public String getSecretKey() {
        return secretKey;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}
