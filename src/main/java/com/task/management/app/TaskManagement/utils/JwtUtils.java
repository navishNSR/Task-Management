package com.task.management.app.TaskManagement.utils;

import com.task.management.app.TaskManagement.model.entries.User;
import com.task.management.app.TaskManagement.repository.UserRepository;
import com.task.management.app.TaskManagement.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    @Value("${jwt.secret_key}")
    private String secret_key;

    @Value("${jwt.expiration}")
    private long expiration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    private Key getSigningKey() {
        byte[] keyBytes = secret_key.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(String username) {
        String token = redisService.getTokenByUsername(username);
        if (token != null && !token.isEmpty()){
            return token;
        }
        Map<String, Object> claims = new HashMap<>();
        User user = userRepository.findByName(username);
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("roles", user.getRole());
        claims.put("exp", expiration);
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String tokenUserName = getUserNameFromToken(token);
        return (tokenUserName.equals(username) && !isTokenExpired(token) && redisService.getTokenByUsername(username) != null);
    }

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret_key.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}

