package com.chrisV.tasktracker.backend.dto;

import com.chrisV.tasktracker.backend.model.Project;

public class ProjectDTO {
    private Long id;
    private String name;
    private UserDTO user;

    public ProjectDTO() {}
    public ProjectDTO(Project project) {
        this.id = project.getId(); 
        this.name = project.getName();
    }

    public UserDTO getUser() {return user;}
    public void setUser(UserDTO user) {this.user = user;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
