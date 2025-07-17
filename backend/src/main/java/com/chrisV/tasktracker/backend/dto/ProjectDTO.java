package com.chrisV.tasktracker.backend.dto;

public class ProjectDTO {
    private Long id;
    private String name;
    private UserDTO user;

    public ProjectDTO() {}
    public ProjectDTO(Long id, String name) {
        this.id = id; 
        this.name = name;
    }

    public UserDTO getUser() {return user;}
    public void setUser(UserDTO user) {this.user = user;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
