package com.chrisV.tasktracker.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.chrisV.tasktracker.backend.dto.PatchUserDTO;
import com.chrisV.tasktracker.backend.dto.UserDTO;
import com.chrisV.tasktracker.backend.exception.DuplicateEmailException;
import com.chrisV.tasktracker.backend.exception.ResourceNotFoundException;
import com.chrisV.tasktracker.backend.mapper.UserMapper;
import com.chrisV.tasktracker.backend.model.User;
import com.chrisV.tasktracker.backend.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepo;

    public List<UserDTO> getUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                    .map(UserMapper::fromEntitySimpleUser) 
                    .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("User with ID: " + id + " not found"));
        return UserMapper.fromEntitySimpleUser(user);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.toEntityUser(userDTO);
        try{
            userRepo.save(user);
        } catch(DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exist");
        }
        return UserMapper.fromEntitySimpleUser(user);
    }

    //receive DTO object to update existing entity
    public UserDTO updateUser(Long id, UserDTO data) {
        User user = userRepo.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("User with ID: " + id + " not found"));

        UserMapper.updateEntityUser(user, data);
        User saved;
        try{
            saved = userRepo.save(user);
        } catch(DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exist");
        }
        return UserMapper.fromEntitySimpleUser(saved);
    }

    public UserDTO patchUser(Long id, PatchUserDTO data) {
        User user = userRepo.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("User with ID: " + id + " not found"));

        if(data.getEmail() != null) user.setEmail(data.getEmail());
        if(data.getName() != null) user.setName(data.getName());

        User saved;
        try{
            saved = userRepo.save(user);
        } catch(DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exist");
        }
        return UserMapper.fromEntitySimpleUser(saved);
    }
}
