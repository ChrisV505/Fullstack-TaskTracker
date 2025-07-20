package com.chrisV.tasktracker.backend.dto;

import com.chrisV.tasktracker.backend.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
