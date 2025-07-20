package com.chrisV.tasktracker.backend.dto;

import com.chrisV.tasktracker.backend.model.User;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchUserDTO {    
    @Email(message = "Must be a valid email")
    private String email;
    private String name;

    public PatchUserDTO() {}
    public PatchUserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
