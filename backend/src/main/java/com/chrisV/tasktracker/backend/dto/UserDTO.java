package com.chrisV.tasktracker.backend.dto;

import com.chrisV.tasktracker.backend.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    
    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    public UserDTO() {}
    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
}
