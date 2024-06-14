package com.task.management.app.TaskManagement.service;


import com.task.management.app.TaskManagement.model.entries.User;
import com.task.management.app.TaskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Converting User to Authenticated User
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user != null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getName())
                    .password(user.getPassword())
                    .build();
//            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not Found: "+username);
    }

}
