package com.chrisV.tasktracker.backend.dto;

import com.chrisV.tasktracker.backend.model.Project;

public class SimpleProjectDTO {
        private Long id;
    private String name;

    public SimpleProjectDTO() {}
    public SimpleProjectDTO(Project project) {
        this.id = project.getId(); 
        this.name = project.getName();
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
