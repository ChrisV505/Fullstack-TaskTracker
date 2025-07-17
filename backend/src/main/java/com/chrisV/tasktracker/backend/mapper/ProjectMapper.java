package com.chrisV.tasktracker.backend.mapper;

import com.chrisV.tasktracker.backend.dto.ProjectDTO;
import com.chrisV.tasktracker.backend.dto.UserDTO;
import com.chrisV.tasktracker.backend.model.Project;

public class ProjectMapper {
    
    public static ProjectDTO fromEntityProject(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setUser(new UserDTO(project.getUser()));
        return dto;
    }


}
