package com.chrisV.tasktracker.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") //allow front-end access
public class TaskController {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private ProjectRepository projectRepo;

    //GET all tasks data
    @GetMapping
    public List<Task> getTasks() {
        System.out.println("GET /api/tasks was called");
        return taskRepo.findAll();
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
    public Task updateTask(@PathVariable Long id, @RequestBody Task data) {
        Task task = taskRepo.findById(id).orElseThrow();
        task.setTitle(data.getTiltle());
        task.setCompleted(data.isCompleted());
        return taskRepo.save(task);
    }

    //DELETE one task entity by id
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepo.existsById(id); 
    }
}
