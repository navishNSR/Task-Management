package com.task.management.app.TaskManagement.controller;

import com.task.management.app.TaskManagement.model.responses.LoginResponse;
import com.task.management.app.TaskManagement.service.RedisService;
import com.task.management.app.TaskManagement.service.UserDetailsServiceImpl;
import com.task.management.app.TaskManagement.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequestMapping("/api/auth")
@RestController
@Slf4j
public class AuthController {

    @Value("${jwt.expiration}")
    private long expiration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request) throws Exception {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
                String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
                byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(credDecoded, StandardCharsets.UTF_8);
                final String[] values = credentials.split(":", 2);

                String username = values[0];
                String password = values[1];

                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final String jwt = jwtUtils.generateToken(userDetails.getUsername());

                // Store Username and token in Redis
//                redisUserSessionManager.saveUserSession(username, jwt, expiration);
                redisService.storeToken(userDetails.getUsername(), jwt, expiration);

                return ResponseEntity.ok(new LoginResponse("Success", "Login Successful", jwt));
            }
        } catch (BadCredentialsException e) {
            log.error("Incorrect username or password", e);
            return new ResponseEntity<>(new LoginResponse("error", "Invalid username or password", null), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Token not generated", HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Extract the token from the request header
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring(7);
        String username = jwtUtils.getUserNameFromToken(token);

        // Invalidate the session
        redisService.deleteToken(username);
        return ResponseEntity.ok("Logged out successfully.");
    }

}
