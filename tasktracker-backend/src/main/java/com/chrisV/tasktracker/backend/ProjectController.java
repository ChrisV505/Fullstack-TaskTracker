package com.chrisV.tasktracker.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectWithTasks(@PathVariable Long id) {
        Optional<Project> projectOpt = projectRepo.findById(id);
        if(!projectOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectOpt.get());
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectRepo.save(project);
    }

    


}
