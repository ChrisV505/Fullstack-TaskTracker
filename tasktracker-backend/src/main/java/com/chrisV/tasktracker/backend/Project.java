package com.chrisV.tasktracker.backend;

import jakarta.persistence.*;

@Entity
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

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
