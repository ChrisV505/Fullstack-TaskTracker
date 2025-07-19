package com.chrisV.tasktracker.backend.mapper;

import com.chrisV.tasktracker.backend.dto.ProjectDTO;
import com.chrisV.tasktracker.backend.dto.SimpleProjectDTO;
import com.chrisV.tasktracker.backend.dto.UserDTO;
import com.chrisV.tasktracker.backend.model.Project;
import com.chrisV.tasktracker.backend.model.User;

public class ProjectMapper {
    
    public static SimpleProjectDTO fromEntityProjectSimple(Project project) {
        SimpleProjectDTO dto = new SimpleProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        return dto;
    }

    public static ProjectDTO fromEntityProjectNestUser(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setUser(new UserDTO(project.getUser()));
        return dto;
    }



    public static void updateProjectEntity(Project project, ProjectDTO dto, User user) {
        project.setName(dto.getName());
        project.setUser(user);
    }


    


}
