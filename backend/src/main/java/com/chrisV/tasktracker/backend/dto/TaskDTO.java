package com.chrisV.tasktracker.backend.dto;

import java.time.LocalDate;

import com.chrisV.tasktracker.backend.model.Priority;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    public TaskDTO() {}

    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Completion is required")
    private Boolean completed;

    @Future(message = "DueDate cannot be in the past")
    @NotNull(message = "DueDate is required")
    private LocalDate dueDate;
    
    private SimpleProjectDTO project;
}