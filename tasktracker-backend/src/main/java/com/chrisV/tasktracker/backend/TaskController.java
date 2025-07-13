package com.chrisV.tasktracker.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") //allow front-end access
public class TaskController {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping
    public List<Task> getTasks() {
        System.out.println("GET /api/tasks was called");
        return taskRepo.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        if(task.getProject() != null && task.getProject().getId() != null) {
            Long projectId = task.getProject().getId();

            //fetch project from DB
            Project realProject = projectRepo.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project with ID: " + projectId + "does not exist"));

            //set new real project 
            task.setProject(realProject);
        }
        return taskRepo.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task data) {
        Task task = taskRepo.findById(id).orElseThrow();
        task.setTitle(data.getTiltle());
        task.setCompleted(data.isCompleted());
        return taskRepo.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepo.existsById(id); 
    
    }
}
