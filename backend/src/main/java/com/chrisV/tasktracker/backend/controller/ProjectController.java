package com.chrisV.tasktracker.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chrisV.tasktracker.backend.Exception.ResourceNotFoundException;
import com.chrisV.tasktracker.backend.dto.ProjectDTO;
//import com.chrisV.tasktracker.backend.dto.ProjectDTO;
import com.chrisV.tasktracker.backend.dto.SimpleProjectDTO;
import com.chrisV.tasktracker.backend.mapper.ProjectMapper;
import com.chrisV.tasktracker.backend.model.Project;
import com.chrisV.tasktracker.backend.model.User;
import com.chrisV.tasktracker.backend.repository.ProjectRepository;
import com.chrisV.tasktracker.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    //GET all projects
    @GetMapping
    public List<SimpleProjectDTO> getProjects() {
        List<Project> projects = projectRepo.findAll();

        return projects.stream()
                        .map(ProjectMapper::fromEntityProjectSimple)
                        .collect(Collectors.toList());
    }

    //GET one project data
    @GetMapping("/{id}")
    public ResponseEntity<SimpleProjectDTO> getProjectWithTasks(@PathVariable Long id) {
        Project project = projectRepo.findById(id).orElseThrow(() 
                        -> new ResourceNotFoundException("Project with id: " + id + " not found"));
        return ResponseEntity.ok(ProjectMapper.fromEntityProjectSimple(project));
    }

    //filter by user id
    @GetMapping("/user/{userId}")
    public List<SimpleProjectDTO> getProjectsByUser(@PathVariable Long userId) {
        List<Project> projects = projectRepo.findByUserId(userId);

        return projects.stream()
                        .map(ProjectMapper::fromEntityProjectSimple)
                        .collect(Collectors.toList());
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

    //Update entire project entity by id 
    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO data) {
        Project project = projectRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project with ID: " + id + " not found"));
        
        User user = userRepo.findById(data.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + data.getUser().getId() + " not found"));
        //update existing project using DTO and User
        ProjectMapper.updateProjectEntity(project, data, user);
        Project saved = projectRepo.save(project);
        return ProjectMapper.fromEntityProjectNestUser(saved);    
    }

    //Update part of project by id
    @PatchMapping("/{id}")
    public SimpleProjectDTO patchTask(@PathVariable Long id, @RequestBody ProjectDTO data) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID: " + id + " not found"));

        if(data.getName() != null) project.setName(data.getName());
        if(data.getUser() != null) {
            User user = userRepo.findById(data.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + data.getUser().getId() + " not found"));
            project.setUser(user);
        }
        Project saved = projectRepo.save(project);
        return ProjectMapper.fromEntityProjectSimple(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectRepo.existsById(id);
        projectRepo.deleteById(id);

        throw new ResourceNotFoundException("project with Id: " + id + " does not exist");
    }
}
