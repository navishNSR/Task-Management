package com.task.management.app.TaskManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void saveToken(String username, String token) {
        redisTemplate.opsForValue().set(username, token);
    }

    public String getToken(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    public void deleteToken(String username) {
        redisTemplate.delete(username);
    }
}
