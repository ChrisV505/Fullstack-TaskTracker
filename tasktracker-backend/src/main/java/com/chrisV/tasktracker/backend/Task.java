package com.chrisV.tasktracker.backend;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private LocalDate dueDate;
    private String priority;
    private String title;
    private boolean completed;

    @ManyToOne
    private Project project;

    public Task() {}

    public Task(String tile, boolean completed) {
        this.title = tile;
        this.completed = completed;
    }
    
    //getters
    public String getDescription() {return description;}
    public LocalDate getDueDate() {return dueDate;}
    public String priority() {return priority;}
    public Long getId() {return id;}
    public String getTiltle() {return title;}
    public boolean isCompleted() {return completed;}

    //setters
    public void setDescription(String description) {this.description = description;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}
    public void setPriority(String priority) {this.priority = priority;}
    public void setId(Long id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setCompleted(boolean completed) {this.completed = completed;}
}
