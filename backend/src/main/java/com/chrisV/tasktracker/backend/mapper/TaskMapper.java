package com.chrisV.tasktracker.backend.mapper;

import com.chrisV.tasktracker.backend.dto.SimpleProjectDTO;
import com.chrisV.tasktracker.backend.dto.SimpleTaskDTO;
import com.chrisV.tasktracker.backend.dto.TaskDTO;
import com.chrisV.tasktracker.backend.model.Project;
import com.chrisV.tasktracker.backend.model.Task;

public class TaskMapper {

    // Convert Task entity to TaskDTO
    public static TaskDTO fromEntityNest(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTiltle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setCompleted(task.isCompleted());
        dto.setProject(new SimpleProjectDTO(task.getProject()));
        return dto;
    }

    public static SimpleTaskDTO fromEntitySimple(Task task) {
        SimpleTaskDTO dto = new SimpleTaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTiltle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setCompleted(task.isCompleted());
        return dto;
    }


    // Convert TaskDTO to Task entity (needs Project passed in)
    public static Task toEntity(TaskDTO dto, Project project) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setCompleted(dto.isCompleted());
        task.setProject(project);
        return task;
    }

    // Optional: update existing Task in-place instead of creating new one
    public static void updateEntity(Task task, TaskDTO dto, Project project) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setCompleted(dto.isCompleted());
        task.setProject(project);
    }
}

