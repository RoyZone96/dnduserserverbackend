package com.dndbackendlayer.dndbackend.controller;

import com.dndbackendlayer.dndbackend.model.User;
import com.dndbackendlayer.dndbackend.repo.UserRepo;
import com.dndbackendlayer.dndbackend.exception.UserNotFoundException;

import java.util.List;

import com.dndbackendlayer.dndbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@CrossOrigin
@RequestMapping("users")
@Validated //see about this later
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    ResponseEntity<User> newUser(@RequestBody User newUser) {
        return new ResponseEntity<>(userService.newUser(newUser), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("user/id/{id}")
    User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/user/username/{username}")
    User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/user/email/{email}")
    User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

}