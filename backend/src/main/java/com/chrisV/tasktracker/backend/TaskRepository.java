package com.chrisV.tasktracker.backend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrderByDueDateAsc();
    List<Task> findAllByOrderByDueDateDesc();
    List<Task> findByPriority(Priority priority);
    List<Task> findByPriorityAndCompleted(Priority priority, boolean isCompleted);
}
