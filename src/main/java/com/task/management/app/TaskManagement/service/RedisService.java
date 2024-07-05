package com.task.management.app.TaskManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void storeToken(String username, String token, long expiration) {
        redisTemplate.opsForValue().set(username, token, expiration, TimeUnit.MILLISECONDS);
    }

    public String getTokenByUsername(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    public void deleteToken(String username) {
        redisTemplate.delete(username);
    }
}
