package com.chrisV.tasktracker.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisV.tasktracker.backend.dto.PatchProjectDTO;
import com.chrisV.tasktracker.backend.dto.ProjectDTO;
import com.chrisV.tasktracker.backend.dto.SimpleProjectDTO;
import com.chrisV.tasktracker.backend.exception.ResourceNotFoundException;
import com.chrisV.tasktracker.backend.mapper.ProjectMapper;
import com.chrisV.tasktracker.backend.mapper.UserMapper;
import com.chrisV.tasktracker.backend.model.Project;
import com.chrisV.tasktracker.backend.model.User;
import com.chrisV.tasktracker.backend.repository.ProjectRepository;
import com.chrisV.tasktracker.backend.repository.UserRepository;

@Service
public class ProjectService {
    
    @Autowired
    ProjectRepository projectRepo;

    @Autowired
    UserRepository userRepo;

    public List<SimpleProjectDTO> getProjects() {
        List<Project> projects = projectRepo.findAll();
        return projects.stream()
                        .map(ProjectMapper::fromEntityProjectSimple)
                        .collect(Collectors.toList());
    }

    public SimpleProjectDTO getProjectById(Long id) {
        Project project = projectRepo.findById(id).orElseThrow(() 
                -> new ResourceNotFoundException("Project with id: " + id + " not found"));
        return ProjectMapper.fromEntityProjectSimple(project);
    }

    public List<ProjectDTO> getProjectsByUser(Long userId) {
        List<Project> projects = projectRepo.findByUserId(userId);
        return projects.stream()
                        .map(ProjectMapper::fromEntityProjectNestUser) 
                        .collect(Collectors.toList());
    }

    public ProjectDTO registerProject(ProjectDTO data) {
        Long userId = data.getUser().getId();
        User user = userRepo.findById(userId).orElseThrow(() 
                    -> new ResourceNotFoundException("User with ID: " + userId + " not found"));

        data.setUser(UserMapper.fromEntitySimpleUser(user));
        Project project = projectRepo.save(ProjectMapper.toEntityProjectNestUser(data, user));
        return ProjectMapper.fromEntityProjectNestUser(project);
    }

    public ProjectDTO updateProject(Long id, ProjectDTO dto) {
        Project project = projectRepo.findById(id).orElseThrow(() 
                    -> new ResourceNotFoundException("User with ID: " + id + " not found"));
        
        User user = userRepo.findById(dto.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + dto.getUser().getId() + " not found"));

        ProjectMapper.updateProjectEntity(project, dto, user);
        projectRepo.save(project);
        return ProjectMapper.fromEntityProjectNestUser(project);
    }

    public ProjectDTO patchTask(Long id, PatchProjectDTO data) {
        Project project = projectRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Project with ID: " + id + " not found"));

        if(data.getName() != null) project.setName(data.getName());
        if(data.getUser() != null) {
            User user = userRepo.findById(data.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + data.getUser().getId() + " not found"));
            project.setUser(user);
        }
        Project saved = projectRepo.save(project);
        return ProjectMapper.fromEntityProjectNestUser(saved);
    }

    public boolean deleteProject(Long id) {
        if(!projectRepo.existsById(id)) return false;
        projectRepo.deleteById(id);
        return true;
    }
}