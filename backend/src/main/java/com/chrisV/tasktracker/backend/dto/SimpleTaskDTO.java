package com.chrisV.tasktracker.backend.dto;

import java.time.LocalDate;

import com.chrisV.tasktracker.backend.model.Priority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleTaskDTO {
    public SimpleTaskDTO() {}

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Boolean completed;
    private LocalDate dueDate;
}
