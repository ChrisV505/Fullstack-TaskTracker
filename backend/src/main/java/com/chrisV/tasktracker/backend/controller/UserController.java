package com.chrisV.tasktracker.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisV.tasktracker.backend.dto.UserDTO;
import com.chrisV.tasktracker.backend.mapper.UserMapper;
import com.chrisV.tasktracker.backend.model.User;
import com.chrisV.tasktracker.backend.repository.UserRepository;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepo;

    //GET all userDTO
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        System.out.println("GET /api/users was called");
        List<User> users = userRepo.findAll();
        
        if(!users.isEmpty()){
        return ResponseEntity.ok(users.stream()
                            .map(UserMapper::fromEntitySimpleUser)
                            .collect(Collectors.toList()));
        }
        return ResponseEntity.badRequest().build();
    }

    //post DTO from request to repo
    @PostMapping
    public UserDTO createUser(@RequestBody @Valid UserDTO userDTO) {
        //save converted
        User user = UserMapper.toEntityUser(userDTO);
        userRepo.save(user);
        return UserMapper.fromEntitySimpleUser(user);
    }
}