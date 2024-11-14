package com.example.demo.common.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOps;
    private final ListOperations<String, Object> listOps;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.listOps = redisTemplate.opsForList();
    }

    @Override
    public void saveValue(String key, String value) {
        valueOps.set(key, value);
    }

    @Override
    public String getValue(String key) {
        return (String) valueOps.get(key);
    }

    @Override
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void pushToList(String key, String value) {
        listOps.rightPush(key, value);
    }
    
    //인터페이스 추가 필요
    @Override
    public void saveToken(String token, Object userInfo) {
        redisTemplate.opsForValue().set(token, userInfo, 1, TimeUnit.HOURS); // 1시간 만료
    }
    
    //키 값 확인
    @Override 
    public boolean getTokenKey(String token) {
        if (redisTemplate.hasKey(token)) {
            return redisTemplate.hasKey(token);
        }
        return false; // 또는 토큰이 없을 때의 처리를 필요에 따라 추가
    }
    
    @Override
    public String getUserIdFromToken(String token) {
        return (String) redisTemplate.opsForValue().get(token);
    }
    //토큰 값 지우기
    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
    
}
