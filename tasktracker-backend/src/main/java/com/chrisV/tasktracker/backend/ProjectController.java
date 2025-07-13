package com.chrisV.tasktracker.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping
    public List<Project> getProjects() {
        System.out.println("GET /api/tasks/projects was called");
        return projectRepo.findAll();
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectRepo.save(project);
    }

    


}
