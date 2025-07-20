package com.chrisV.tasktracker.backend.dto;

import com.chrisV.tasktracker.backend.model.Project;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleProjectDTO {
    private Long id;
    
    @NotBlank(message = "Project name is required")
    private String name;

    public SimpleProjectDTO() {}
    public SimpleProjectDTO(Project project) {
        this.id = project.getId(); 
        this.name = project.getName();
    }
}
