package com.chrisV.tasktracker.tasktracker_backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {}
