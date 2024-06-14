package com.task.management.app.TaskManagement.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

@Component
@Service
public class RedisUserSessionManager {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${jwt.expiration}")
    private String expiration;

    @Autowired
    private JedisPool jedisPool;

    public RedisUserSessionManager() {
        jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
    }

    public void saveUserSession(String username, String token, long expiration) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = username;
            Map<String, String> sessionData = new HashMap<>();
            sessionData.put("token", token);
            sessionData.put("expiration", String.valueOf(expiration));
            jedis.hmset(key, sessionData);
        }
    }

    public Map<String, String> getUserSession(String username) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(username);
        }
    }

    public String getToken(String username) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = username;
            return jedis.hget(key, "token");
        }
    }

    public void deleteUserSession(String username) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = username;
            jedis.del(key);
        }
    }

    public Boolean isTokenValid(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String token = getToken(username);
        if (token != null){
            return true;
        }
        return false;
    }
}
