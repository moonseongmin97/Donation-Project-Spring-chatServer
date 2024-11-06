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
    public void saveToken(String token, String userId) {
        redisTemplate.opsForValue().set(token, userId, 1, TimeUnit.HOURS); // 1시간 만료
    }
    @Override
    public String getUserIdFromToken(String token) {
        return (String) redisTemplate.opsForValue().get(token);
    }
    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
    
}
