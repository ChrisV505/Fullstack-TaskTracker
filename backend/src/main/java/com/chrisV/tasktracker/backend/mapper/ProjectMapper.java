package com.chrisV.tasktracker.backend.mapper;

import com.chrisV.tasktracker.backend.dto.ProjectDTO;
import com.chrisV.tasktracker.backend.dto.SimpleProjectDTO;
import com.chrisV.tasktracker.backend.dto.UserDTO;
import com.chrisV.tasktracker.backend.model.Project;

public class ProjectMapper {
    
    public static SimpleProjectDTO fromEntityProjectSimplePj(Project project) {
        SimpleProjectDTO dto = new SimpleProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        return dto;
    }

    public static ProjectDTO fromEntityProjectNestPj(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setUser(new UserDTO(project.getUser()));
        return dto;
    }

    


}
