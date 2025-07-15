package com.chrisV.tasktracker.backend.dto;

import java.time.LocalDate;

import com.chrisV.tasktracker.backend.model.Priority;

public class TaskDTO {

    public TaskDTO() {}

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Boolean completed;
    private LocalDate dueDate;
    private ProjectDTO project;

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Boolean isCompleted() {return completed;}
    public void setCompleted(Boolean completed) {this.completed = completed;}

    public LocalDate getDueDate() {return dueDate;}
    public void setDueDate(LocalDate duedate) {this.dueDate = duedate;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    
    public Priority getPriority() {return priority;}
    public void setPriority(Priority priority) {this.priority = priority;}

    public ProjectDTO getProject() {return project;}
    public void setProject(ProjectDTO project) {this.project = project;}
}