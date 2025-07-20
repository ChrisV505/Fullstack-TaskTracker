package com.chrisV.tasktracker.backend.dto;

import com.chrisV.tasktracker.backend.model.Project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchProjectDTO {
    private Long id;
    private String name;
    private UserDTO user;

    public PatchProjectDTO() {}
    public PatchProjectDTO(Project project) {
        this.id = project.getId(); 
        this.name = project.getName();
    }
}
