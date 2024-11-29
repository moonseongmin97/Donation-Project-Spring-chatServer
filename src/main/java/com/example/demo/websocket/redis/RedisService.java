package com.example.demo.websocket.redis;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class RedisService{

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
    
    //인터페이스 추가 필요
    
    public void saveToken(String token, Object userInfo) {
        redisTemplate.opsForValue().set(token, (String) userInfo, 1, TimeUnit.HOURS); // 1시간 만료
    }
    
    //로그인 키 값 확인

    public boolean getTokenKey(String token) {
        if (redisTemplate.hasKey(token)) {
            return redisTemplate.hasKey(token);
        }
        return false; // 또는 토큰이 없을 때의 처리를 필요에 따라 추가
    }
 
    public String getUserIdFromToken(String token) {
        return (String) redisTemplate.opsForValue().get(token);
    }
    //토큰 값 지우기
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
    
    //토큰 시간 연장
    public void addTokenTime(String token , int time) {
    	redisTemplate.expire(token, Duration.ofMinutes(time)); //활동 중이면 토큰 시간 연장
    }
}
