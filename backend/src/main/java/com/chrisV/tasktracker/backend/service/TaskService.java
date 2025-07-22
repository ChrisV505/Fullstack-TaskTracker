package com.chrisV.tasktracker.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisV.tasktracker.backend.dto.PatchTaskDTO;
import com.chrisV.tasktracker.backend.dto.SimpleTaskDTO;
import com.chrisV.tasktracker.backend.dto.TaskDTO;
import com.chrisV.tasktracker.backend.exception.ResourceNotFoundException;
import com.chrisV.tasktracker.backend.mapper.ProjectMapper;
import com.chrisV.tasktracker.backend.mapper.TaskMapper;
import com.chrisV.tasktracker.backend.model.Task;
import com.chrisV.tasktracker.backend.repository.ProjectRepository;
import com.chrisV.tasktracker.backend.repository.TaskRepository;
import com.chrisV.tasktracker.backend.model.Priority;
import com.chrisV.tasktracker.backend.model.Project;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private ProjectRepository projectRepo;

    public List<SimpleTaskDTO> getTasks() {
        List<Task> tasks = taskRepo.findAll();
        return tasks.stream()
                    .map(TaskMapper::fromEntitySimpleTask)
                    .collect(Collectors.toList());
    }

    public SimpleTaskDTO getTaskById(Long id) {
        Task task = taskRepo.findById(id).orElseThrow(() 
                    -> new ResourceNotFoundException("task of ID: " + id + " not found"));
        return TaskMapper.fromEntitySimpleTask(task);
    }

    public List<SimpleTaskDTO> getTasksByProject(Long projectId) {
            List<Task> tasks = taskRepo.findByProjectId(projectId);
            return tasks.stream()
                .map(TaskMapper::fromEntitySimpleTask)
                .collect(Collectors.toList());
    }

    public List<SimpleTaskDTO> filterbyPriorityAndCompletion(String priority, Boolean completed) {
        Priority priorityEnum = Priority.valueOf(priority.toUpperCase());
        List<Task> tasks = taskRepo.findByPriorityAndCompleted(priorityEnum, completed);

        return tasks.stream()
                    .map(TaskMapper::fromEntitySimpleTask)
                    .collect(Collectors.toList());
    }

    public List<SimpleTaskDTO> sortByDueDateDirection(String direction) {
        List<Task> tasks = null; 

        //fill tasks list depending on requestparam
        if(direction.equals("asc")) tasks = taskRepo.findAllByOrderByDueDateAsc(); 
        else if(direction.equals("desc")) tasks = taskRepo.findAllByOrderByDueDateDesc();

        return tasks.stream()
                    .map(TaskMapper::fromEntitySimpleTask)
                    .collect(Collectors.toList());
    }

    public TaskDTO registerTask(TaskDTO dto) {
        Long projectId = dto.getProject().getId();
        Project project = projectRepo.findById(projectId).orElseThrow(() 
                    -> new ResourceNotFoundException("Project with ID: " + projectId + " not found"));

        dto.setProject(ProjectMapper.fromEntityProjectSimple(project));
        Task saved = taskRepo.save(TaskMapper.toEntity(dto, project));
        return TaskMapper.fromEntityNestTask(saved);
    }

    public TaskDTO updateTask(Long id, TaskDTO data) {
        Task existingTask = taskRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + id + " not found"));

        Project project = projectRepo.findById(data.getProject().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + data.getProject().getId() + " not found"));
        // Update the existing task in-place using the DTO and Project
        TaskMapper.updateEntity(existingTask, data, project);

        Task saved = taskRepo.save(existingTask);
        return TaskMapper.fromEntityNestTask(saved);
    }

    public TaskDTO patchTask(Long id, PatchTaskDTO data) {
        Task task = taskRepo.findById(id).orElseThrow(() 
                        -> new ResourceNotFoundException("Task with ID " + id + " not found"));

        if (data.getTitle() != null) task.setTitle(data.getTitle());
        if (data.getDescription() != null) task.setDescription(data.getDescription());
        if (data.getPriority() != null) task.setPriority(data.getPriority());
        if (data.getDueDate() != null) task.setDueDate(data.getDueDate());
        if (data.getCompleted() != null) task.setCompleted(data.getCompleted());
        if (data.getProject() != null) {
            Project project = projectRepo.findById(data.getProject().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + data.getProject().getId() + " not found"));
            task.setProject(project);
        }

        Task saved = taskRepo.save(task);
        return TaskMapper.fromEntityNestTask(saved);
    }

    public boolean deleteTask(Long id) {
        if(!taskRepo.existsById(id)) return false;
        taskRepo.deleteById(id);
        return true;
    }
}