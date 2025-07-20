package com.chrisV.tasktracker.backend.dto;

import java.time.LocalDate;

import com.chrisV.tasktracker.backend.model.Priority;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchTaskDTO {

    public PatchTaskDTO() {}

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Boolean completed;

    @FutureOrPresent(message = "DueDate cannot be in the past")
    private LocalDate dueDate;
    
    private SimpleProjectDTO project;
}
