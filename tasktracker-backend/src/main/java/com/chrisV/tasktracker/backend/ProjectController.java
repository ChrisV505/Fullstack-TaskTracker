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

    //GET all projects
    @GetMapping
    public List<Project> getProjects() {
        System.out.println("GET /api/tasks/projects was called");
        return projectRepo.findAll();
    }

    //GET one project data
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectWithTasks(@PathVariable Long id) {
        Optional<Project> projectOpt = projectRepo.findById(id);
        if(!projectOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectOpt.get());
    }

    //POST one project entity
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectRepo.save(project);
    }

    //Update project entity by id
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project data) {
        Project project = projectRepo.findById(id).orElseThrow();
        project.setName(data.getName());
        return projectRepo.save(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectRepo.existsById(id);
    }


}
