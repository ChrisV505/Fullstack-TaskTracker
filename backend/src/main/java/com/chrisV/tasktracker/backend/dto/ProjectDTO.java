package com.chrisV.tasktracker.backend.dto;

import com.chrisV.tasktracker.backend.model.Project;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {
    private Long id;

    @NotBlank(message = "Project name is required")
    private String name;
    private UserDTO user;

    public ProjectDTO() {}
    public ProjectDTO(Project project) {
        this.id = project.getId(); 
        this.name = project.getName();
    }
}
