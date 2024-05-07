package com.dndbackendlayer.dndbackend.services;

import java.util.Optional;

import com.dndbackendlayer.dndbackend.model.User;
import com.dndbackendlayer.dndbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public boolean authenticateUser(String username, String plainPassword) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(plainPassword, user.getPassword());
        }
        return false;
    }
}