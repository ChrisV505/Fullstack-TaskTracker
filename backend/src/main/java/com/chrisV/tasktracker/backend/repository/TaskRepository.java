package com.chrisV.tasktracker.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisV.tasktracker.backend.model.Priority;
import com.chrisV.tasktracker.backend.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrderByDueDateAsc();
    List<Task> findAllByOrderByDueDateDesc();
    List<Task> findByPriority(Priority priority);
    List<Task> findByPriorityAndCompleted(Priority priority, boolean isCompleted);
    List<Task> findByProjectId(Long projectId);
}
