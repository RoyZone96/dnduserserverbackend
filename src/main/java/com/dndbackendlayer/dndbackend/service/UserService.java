package com.dndbackendlayer.dndbackend.service;

import com.dndbackendlayer.dndbackend.exception.UserNotFoundException;
import com.dndbackendlayer.dndbackend.model.User;
import com.dndbackendlayer.dndbackend.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepo userRepo;

    public User newUser(User newUser) {
        System.out.println("The method did get called, yo");
        System.out.println(newUser.getEmail() + " " + newUser.getPassword() + " " + newUser.getUsername());
        return userRepo.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepo.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}
