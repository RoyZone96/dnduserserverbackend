package com.dndbackendlayer.dndbackend.controller;

import com.dndbackendlayer.dndbackend.model.User;
import com.dndbackendlayer.dndbackend.repo.UserRepo;
import com.dndbackendlayer.dndbackend.exception.InvalidCredentialsException;
import com.dndbackendlayer.dndbackend.exception.UserNotFoundException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return userRepo.save(newUser);
    }

    @PostMapping("/user/login")
    public User login(@RequestBody User loginUser) {
        // Find the user by username
        User user = userRepo.findByUsername(loginUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // If the password is incorrect, throw an exception
        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // If the user exists and the password is correct, return the user
        return user;
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("user/id/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/user/username/{username}")
    User getUserByUsername(@PathVariable String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @GetMapping("/user/email/{email}")
    User getUserByEmail(@PathVariable String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

}