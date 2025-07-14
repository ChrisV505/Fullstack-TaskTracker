package com.chrisV.tasktracker.backend;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Task> tasks;

    //constructors
    public Project() {}

    public Project(String name) {
        this.name = name;
    }

    //getters and setters
    public Long getId() {return id;}
    public String getName() {return name;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
}
