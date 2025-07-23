package com.chrisV.tasktracker.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chrisV.tasktracker.backend.dto.PatchTaskDTO;
import com.chrisV.tasktracker.backend.dto.SimpleTaskDTO;
import com.chrisV.tasktracker.backend.dto.TaskDTO;
import com.chrisV.tasktracker.backend.service.TaskService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173") //allow front-end access
public class TaskController {

    @Autowired
    private TaskService taskService;

    //GET all tasks data
    @GetMapping
    public ResponseEntity<List<SimpleTaskDTO>> getTasks() {
        List<SimpleTaskDTO> tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    //GET one task by Id
    @GetMapping("{id}")
    public ResponseEntity<SimpleTaskDTO> getTask(@PathVariable Long id) {
        SimpleTaskDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    //get task by projec id
    @GetMapping("project/{projectId}")
        public ResponseEntity<List<SimpleTaskDTO>> getTasksByProject(@PathVariable Long projectId) {
            List<SimpleTaskDTO> tasks = taskService.getTasksByProject(projectId);
            return ResponseEntity.ok(tasks);
        }

    @GetMapping("/filter") 
    public ResponseEntity<List<SimpleTaskDTO>> filterbyPriorityAndCompletion(
        @RequestParam (defaultValue = "HIGH", required = false) String priority, 
        @RequestParam (defaultValue = "false", required = false) Boolean completed) {

        List<SimpleTaskDTO> tasks = taskService.filterbyPriorityAndCompletion(priority, completed);
        return ResponseEntity.ok(tasks);
    }

    //sort by direction param
    @GetMapping("/sort")
    public ResponseEntity<List<SimpleTaskDTO>> sortByDueDateDirection(@RequestParam String direction) {
        List<SimpleTaskDTO> tasks = taskService.sortByDueDateDirection(direction);
        return ResponseEntity.ok(tasks);
    }

    //POST one task entity
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO dto) {
        TaskDTO task = taskService.registerTask(dto);
        return ResponseEntity.ok(task);
    }

    //UPDATE one task entity by id
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO data) {
        TaskDTO task = taskService.updateTask(id, data);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> patchTask(@PathVariable Long id, @Valid @RequestBody PatchTaskDTO data) {
        TaskDTO task = taskService.patchTask(id, data);
        return ResponseEntity.ok(task);
    }

    //DELETE one task entity by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
