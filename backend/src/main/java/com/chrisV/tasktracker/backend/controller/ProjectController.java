package com.chrisV.tasktracker.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chrisV.tasktracker.backend.dto.PatchProjectDTO;
import com.chrisV.tasktracker.backend.dto.ProjectDTO;
import com.chrisV.tasktracker.backend.dto.SimpleProjectDTO;
import com.chrisV.tasktracker.backend.service.ProjectService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    //GET all projects
    @GetMapping
    public ResponseEntity<List<SimpleProjectDTO>> getProjects() {
        List<SimpleProjectDTO> projects = projectService.getProjects();
        return ResponseEntity.ok(projects);
    }

    //GET one project data
    @GetMapping("/{id}")
    public ResponseEntity<SimpleProjectDTO> getProjectById(@PathVariable Long id) {
        SimpleProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    //filter by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUser(@PathVariable Long userId) {
        List<ProjectDTO> projects = projectService.getProjectsByUser(userId);
        return ResponseEntity.ok(projects); 
    }

    //POST one project entity
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO project) {
        ProjectDTO dto = projectService.registerProject(project);
        return ResponseEntity.ok(dto);
    }

    //Update entire project entity by id 
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDTO data) {
        ProjectDTO project = projectService.updateProject(id, data);
        return ResponseEntity.ok(project); 
    }

    //Update part of project by id
    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDTO> patchTask(@PathVariable Long id, @Valid @RequestBody PatchProjectDTO data) {
        ProjectDTO project = projectService.patchTask(id, data);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        boolean deleted = projectService.deleteProject(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}