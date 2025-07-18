package com.chrisV.tasktracker.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chrisV.tasktracker.backend.Exception.ResourceNotFoundException;
import com.chrisV.tasktracker.backend.dto.SimpleTaskDTO;
import com.chrisV.tasktracker.backend.dto.TaskDTO;
import com.chrisV.tasktracker.backend.mapper.TaskMapper;
import com.chrisV.tasktracker.backend.model.Priority;
import com.chrisV.tasktracker.backend.model.Project;
import com.chrisV.tasktracker.backend.model.Task;
import com.chrisV.tasktracker.backend.repository.ProjectRepository;
import com.chrisV.tasktracker.backend.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173") //allow front-end access
public class TaskController {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private ProjectRepository projectRepo;

    //GET all tasks data
    @GetMapping
    public List<SimpleTaskDTO> getTasks() {
        List<Task> tasks = taskRepo.findAll();
        return tasks.stream()
                    .map(TaskMapper::fromEntitySimpleTask)
                    .collect(Collectors.toList());
    }

    //GET one task by Id
    @GetMapping("{id}")
    public ResponseEntity<SimpleTaskDTO> getTask(@PathVariable Long id) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                                        "task of ID: " + id + " not found"));

        if(task == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(TaskMapper.fromEntitySimpleTask(task));
    }

    @GetMapping("project/{projectId}")
        public List<SimpleTaskDTO> getTasksByProject(@PathVariable Long projectId) {
            List<Task> tasks = taskRepo.findByProjectId(projectId);
            return tasks.stream()
                        .map(TaskMapper::fromEntitySimpleTask)
                        .collect(Collectors.toList());
        }

    // //filter by priority
    // @GetMapping("/filter")
    // public List<SimpleTaskDTO> filterByPriority(@RequestParam(defaultValue = "HIGH")String priority) {
    //     Priority priorityEnum = Priority.valueOf(priority.toUpperCase());
    //     List<Task> tasks = taskRepo.findByPriority(priorityEnum);

    //     return tasks.stream()
    //                 .map(TaskMapper::fromEntitySimpleTask)
    //                 .collect(Collectors.toList());
    // }

    @GetMapping("/filter") 
    public List<SimpleTaskDTO> filterbyPriorityAndCompletion(
        @RequestParam (defaultValue = "HIGH", required = false) String priority, 
        @RequestParam (defaultValue = "false", required = false) Boolean completed) {

        Priority priorityEnum = Priority.valueOf(priority.toUpperCase());
        List<Task> tasks = taskRepo.findByPriorityAndCompleted(priorityEnum, completed);

        return tasks.stream()
                    .map(TaskMapper::fromEntitySimpleTask)
                    .collect(Collectors.toList());
    }

    //sort by direction param
    @GetMapping("/sort")
    public ResponseEntity<List<SimpleTaskDTO>> sortByDueDateAsc(@RequestParam String direction) {
        List<Task> tasks; 

        //fill tasks list depending on requestparam
        if(direction.equals("asc")) tasks = taskRepo.findAllByOrderByDueDateAsc(); 
        else if(direction.equals("desc")) tasks = taskRepo.findAllByOrderByDueDateDesc();
        else return ResponseEntity.badRequest().build();

        if(tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tasks.stream()
                                    .map(TaskMapper::fromEntitySimpleTask)
                                    .collect(Collectors.toList()));

    }

    //POST one task entity
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Long projectId = task.getProject().getId();

        Optional<Project> projectOpt = projectRepo.findById(projectId);
        if(!projectOpt.isPresent()) {
            return ResponseEntity.badRequest().build() ;
        }
        
        task.setProject((projectOpt.get()));
        Task savedTask = taskRepo.save(task);
        return ResponseEntity.ok(savedTask);
    }

    //UPDATE one task entity by id
    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO data) {
        Task existingTask = taskRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + id + " not found"));

        Project project = projectRepo.findById(data.getProject().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + data.getProject().getId() + " not found"));
        // Update the existing task in-place using the DTO and Project
        TaskMapper.updateEntity(existingTask, data, project);

        Task saved = taskRepo.save(existingTask);
        return TaskMapper.fromEntityNestTask(saved);
    }

    @PatchMapping("/{id}")
    public TaskDTO patchTask(@PathVariable Long id, @RequestBody TaskDTO data) {
        Task task = taskRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + id + " not found"));

        if (data.getTitle() != null) task.setTitle(data.getTitle());
        if(data.getDescription() != null) task.setDescription(data.getDescription());
        if (data.getPriority() != null) task.setPriority(data.getPriority());
        if (data.getDueDate() != null) task.setDueDate(data.getDueDate());
        if (data.isCompleted() != null) task.setCompleted(data.isCompleted());
        if (data.getProject() != null) {
            Project project = projectRepo.findById(data.getProject().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + data.getProject().getId() + " not found"));
            task.setProject(project);
        }

        Task saved = taskRepo.save(task);
        return TaskMapper.fromEntityNestTask(saved);
    }

    //DELETE one task entity by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        if(taskRepo.existsById(id)) {
            taskRepo.deleteById(id);
            return ResponseEntity.ok("task deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not foud");
    }
}
