package com.task.management.app.TaskManagement.controller;

import com.task.management.app.TaskManagement.model.entries.User;
import com.task.management.app.TaskManagement.repository.UserRepository;
import com.task.management.app.TaskManagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in getting all users: " + e);
            return new ResponseEntity<>("Token Expired", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Validated(User.CreateValidationGroup.class) @RequestBody User user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            User userInDb = userRepository.findByName(name);
            if (userInDb.getRole().contains("ADMIN")) {
                return userService.createUser(user);
            } else {
                return new ResponseEntity<>("Only Admin can create user", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Error in creating user: " + e);
            return new ResponseEntity<>("Token Expired", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/update-user-info")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User users = userRepository.findByName(user.getName());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        try {
            return userService.deleteUserById(id);
        } catch (Exception e) {
            log.error("Error in deleting user: " + e);
            return new ResponseEntity<>("Token Expired", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/delete-all-user")
    public ResponseEntity<?> deleteAllUsers() {
        try {
            return userService.deleteAllUsers();
        } catch (Exception e) {
            log.error("Error in deleting all users: " + e);
            return new ResponseEntity<>("Token Expired", HttpStatus.FORBIDDEN);
        }
    }

}
