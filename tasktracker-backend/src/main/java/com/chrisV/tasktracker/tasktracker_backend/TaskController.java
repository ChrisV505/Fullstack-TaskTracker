package com.chrisV.tasktracker.tasktracker_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") //allow front-end access
public class TaskController {

    @Autowired
    private TaskRepository repo;

    @GetMapping
    public List<Task> getTasks() {
        System.out.println("GET /api/tasks was called");
        return repo.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return repo.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task data) {
        Task task = repo.findById(id).orElseThrow();
        task.setTitle(data.getTiltle());
        task.setCompleted(data.isCompleted());
        return repo.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        repo.existsById(id); 
    
    }
}
