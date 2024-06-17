package com.task.management.app.TaskManagement.service;

import com.task.management.app.TaskManagement.model.entries.User;
import com.task.management.app.TaskManagement.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public ResponseEntity<?> createUser(User user){
        List<User> users = userRepository.findAll();
        if (users.stream().anyMatch(x -> x.getName().equals(user.getName()))){
            return new ResponseEntity<>("User Already Present", HttpStatus.CONFLICT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        List<User> usersAfterAdding = userRepository.findAll();
        return new ResponseEntity<>(usersAfterAdding, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteUserById(ObjectId id){
        User user = userRepository.findById(id);
        userRepository.delete(user);
        return new ResponseEntity<>("User deleted...", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteAllUsers(){
        userRepository.deleteAll();
        return new ResponseEntity<>("All Users deleted...", HttpStatus.OK);
    }


}
