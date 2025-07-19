package com.chrisV.tasktracker.backend.dto;

import com.chrisV.tasktracker.backend.model.User;

import jakarta.validation.constraints.Email;

public class PatchUserDTO {    
    @Email(message = "Must be a valid email")
    private String email;
    private String name;

    public PatchUserDTO() {}
    public PatchUserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
