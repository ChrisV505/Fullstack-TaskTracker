package com.chrisV.tasktracker.backend;

import java.time.LocalDate;

public class TaskDTO {
    private Long id;
    private String title;
    private Priority priority;
    private Boolean completed;
    private LocalDate dueDate;
    private Long projectId;
    private String projectName;

    public static TaskDTO fromEntity(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTiltle());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setCompleted(task.isCompleted());
        
        if(task.getProject() != null) {
            dto.setProjectId(task.getProject().getId());
            dto.setProjectName(task.getProject().getName());
        }
        return dto;
    }

    public Boolean getCompleted() {return completed;}
    public void setCompleted(Boolean completed) {this.completed = completed;}

    public LocalDate getDueDate() {return dueDate;}
    public void setDueDate(LocalDate duedate) {this.dueDate = duedate;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    
    public Priority getPriority() {return priority;}
    public void setPriority(Priority priority) {this.priority = priority;}

    public Long getProjectId() {return projectId;}
    public void setProjectId(Long projectId) {this.projectId = projectId;}

    public String getProjectName() {return projectName;}
    public void setProjectName(String projectName) {this.projectName = projectName;}
}
