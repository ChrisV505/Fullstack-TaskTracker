package com.chrisV.tasktracker.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisV.tasktracker.backend.model.User;
import com.chrisV.tasktracker.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepo;

    //GET all users
    public List<User> getUsers() {
        System.out.println("GET /api/users was called");
        return userRepo.findAll();
    }

    



}
