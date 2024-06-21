package com.dndbackendlayer.dndbackend.controller;

import com.dndbackendlayer.dndbackend.model.AuthRequest;
import com.dndbackendlayer.dndbackend.model.User;
import com.dndbackendlayer.dndbackend.repo.UserRepo;
import com.dndbackendlayer.dndbackend.exception.UserNotFoundException;
import com.dndbackendlayer.dndbackend.services.UserService;
import com.dndbackendlayer.dndbackend.services.JwtServices;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService service;

    @Autowired
    private JwtServices jwtServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/users/newuser")
    public String addNewUser(@RequestBody User user) {
        return service.addUser(user);

    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> existingUserOptional = userRepo.findByUsername(user.getUsername());

        if (!existingUserOptional.isPresent()
                || !passwordEncoder.matches(user.getPassword(), existingUserOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }

        User existingUser = existingUserOptional.get();
        // If the passwords match, log the user in

        // Return a successful response
        return ResponseEntity.ok("Login successful.");
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
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

    @PostMapping("/users/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtServices.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}