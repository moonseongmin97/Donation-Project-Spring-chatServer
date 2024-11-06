package com.example.demo.common.redis.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    void saveValue(String key, String value);
    String getValue(String key);
    void deleteValue(String key);
    void pushToList(String key, String value);
    void saveToken(String token, String userId);
    String getUserIdFromToken(String token);
    void deleteToken(String token);
}