package com.chrisV.tasktracker.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chrisV.tasktracker.backend.model.Project;
import com.chrisV.tasktracker.backend.model.User;
import com.chrisV.tasktracker.backend.repository.ProjectRepository;
import com.chrisV.tasktracker.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    //GET all projects
    @GetMapping
    public List<Project> getProjects() {
        System.out.println("GET /api/projects was called");
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
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Long userId = project.getUser().getId();

        Optional<User> userOpt = userRepo.findById(userId);
        if(!userOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        project.setUser((userOpt.get()));
        Project savedProject = projectRepo.save(project);
        return ResponseEntity.ok(savedProject);
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
        projectRepo.deleteById(id);


        throw new IllegalArgumentException("project with Id: " + id + " does not exist");
    }
}
