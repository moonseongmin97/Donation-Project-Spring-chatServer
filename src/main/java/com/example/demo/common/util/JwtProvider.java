package com.example.demo.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.config.JwtConfig;

import java.util.Date;

@Component
public class JwtProvider {

	@Autowired
	 private  JwtConfig jwtConfig;
	
    // JWT 생성
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey())
                .compact();
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
    }

    // JWT에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
