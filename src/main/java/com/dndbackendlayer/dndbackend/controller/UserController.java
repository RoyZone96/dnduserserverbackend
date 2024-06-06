package com.dndbackendlayer.dndbackend.controller;

import com.dndbackendlayer.dndbackend.model.User;
import com.dndbackendlayer.dndbackend.repo.UserRepo;
import com.dndbackendlayer.dndbackend.exception.UserNotFoundException;
import com.dndbackendlayer.dndbackend.services.UserService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    private UserService service;

    @PostMapping("/newuser")
    User newUser(@RequestBody User newUser) {
        service.addUser(newUser);
        return newUser;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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